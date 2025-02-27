package dev.trindadedev.javacompiler;

/*
 * Copyright 2025 Aquiles Trindade.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import com.android.tools.r8.D8;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.internal.compiler.batch.Main;

/*
 * Class that compiles a java code, based on an File Java
 * @author Aquiles Trindade (trindadedev13).
 */
public final class JavaCompiler {

  private final Context context;
  private final List<String> logs = new ArrayList<>();

  public JavaCompiler(Context context) {
    this.context = context;
  }

  /*
   * Compile the code using ECJ.
   * @param compileItem a Item with Info to compile
   */
  public final void compile(final CompileItem compileItem) {
    logs.clear();
    if (!compileItem.getJavaFile().exists()
        || !compileItem.getJavaFile().getName().endsWith(".java")) {
      newLog("Invalid file: " + compileItem.getJavaFile().getAbsolutePath());
      return;
    }

    var errorWriter = new StringWriter();
    var errWriter = new PrintWriter(errorWriter);

    var outputWriter = new StringWriter();
    var outWriter = new PrintWriter(outputWriter);

    var outputDir = new File(compileItem.getOutputDir(), "classes/");
    if (!outputDir.exists() && !outputDir.mkdirs()) {
      newLog("failed create output directory: " + outputDir.getAbsolutePath());
      return;
    }

    var args = new ArrayList<String>();
    args.add("-1.8");
    args.add("-proc:none");
    args.add("-nowarn");
    args.add("-d");
    args.add(outputDir.getAbsolutePath());
    args.add("-sourcepath");
    args.add(" ");
    args.add(compileItem.getJavaFile().getAbsolutePath());
    args.add("-cp");
    args.add(getLibs());

    var compiler = new Main(outWriter, errWriter, false, null, null);
    var success = compiler.compile(args.toArray(new String[0]));

    if (!success) {
      newLog("failed to compile:\n" + errorWriter);
      return;
    }

    newLog("compiled with successful:\n" + outputWriter);

    try {
      var inputPath = outputDir.getAbsolutePath();
      var outputPath = outputDir.getAbsolutePath() + "/classes.jar";
      var jarPackager = new JarCreator(inputPath, outputPath);
      jarPackager.create();
      runD8(outputDir);
    } catch (IOException e) {
      newLog(e.toString());
    }
  }

  private final void runD8(final File outputDir) {
    try {
      var d8Args = new ArrayList<String>();
      d8Args.add("--release");
      d8Args.add("--min-api");
      d8Args.add("21");
      d8Args.add("--lib");
      d8Args.add(getAndroidJarFile().getAbsolutePath());
      d8Args.add("--output");
      d8Args.add(outputDir.getAbsolutePath());
      var classes = getClassFiles(new File(outputDir.getAbsolutePath()));
      for (var file : classes) {
        if (file.getName().endsWith(".class")) {
          d8Args.add(file.getAbsolutePath());
        }
      }

      D8.main(d8Args.toArray(new String[0]));
      run(outputDir);
    } catch (Exception e) {
      newLog(e.toString());
    }
  }

  /*
   * Run the compiled code with R8 & DexClassLoader
   * @param outputDir The path where classes.jar is located
   */
  public final void run(final File outputDir) {
    try {
      var resultStr = new StringBuilder();
      var outputStream =
          new OutputStream() {
            @Override
            public void write(int v) {
              resultStr.append(String.valueOf((char) v));
            }

            @Override
            public String toString() {
              return resultStr.toString();
            }
          };

      System.setOut(new PrintStream(outputStream));
      System.setErr(new PrintStream(outputStream));

      var className = "Main";
      var optimizedDir = context.getDir("odex", Context.MODE_PRIVATE).getAbsolutePath();

      var dexLoader =
          new DexClassLoader(
              outputDir.getAbsolutePath() + "/classes.dex",
              optimizedDir,
              null,
              context.getClassLoader());

      Class<?> calledClass = dexLoader.loadClass(className);
      var method = calledClass.getDeclaredMethod("main", String[].class);
      String[] param = {};
      var result = method.invoke(null, new Object[] {param});
      newLog(resultStr.toString());
    } catch (ClassNotFoundException
        | NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException e) {
      newLog(e.toString());
    }
  }

  private final List<File> getClassFiles(final File dir) {
    var files = new ArrayList<File>();
    File[] fileArr = dir.listFiles();
    if (fileArr == null) {
      return files;
    }

    for (var file : fileArr) {
      if (file.isDirectory()) {
        files.addAll(getClassFiles(file));
      } else {
        files.add(file);
      }
    }

    return files;
  }

  public final void newLog(final String log) {
    logs.add(log);
  }

  public final List<String> getLogs() {
    return logs;
  }

  private final String getLibs() {
    var libs = new StringBuilder();
    libs.append(getAndroidJarFile().getAbsolutePath());
    libs.append(":");
    libs.append(getLambdaFactoryFile().getAbsolutePath());
    return libs.toString();
  }

  private final File getAndroidJarFile() {
    var androidJar = new File(context.getFilesDir() + "/temp/android.jar");

    if (androidJar.exists()) return androidJar;

    Decompress.unzipFromAssets(
        context, "android.jar.zip", androidJar.getParentFile().getAbsolutePath());

    return androidJar;
  }

  private final File getLambdaFactoryFile() {
    var lambdaFactory = new File(context.getFilesDir() + "/temp/core-lambda-stubs.jar");

    if (lambdaFactory.exists()) return lambdaFactory;

    Decompress.unzipFromAssets(
        context, "core-lambda-stubs.zip", lambdaFactory.getParentFile().getAbsolutePath());

    return lambdaFactory;
  }

  public static final class CompileItem {
    private final File javaFile;
    private final File outputDir;

    public CompileItem(final File javaFile, final File outputDir) {
      this.javaFile = javaFile;
      this.outputDir = outputDir;
    }

    public final File getJavaFile() {
      return javaFile;
    }

    public final File getOutputDir() {
      return outputDir;
    }
  }
}
