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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/*
 * basic .jar creator
 * @author Aquiles Trindade (trindadedev).
 */
public class JarCreator {
  private final String input;
  private final String output;
  private final Attributes attributes;

  private static Attributes getDefaultAttributes() {
    var attrs = new Attributes();
    attrs.put(new Attributes.Name("Created-By"), "Sparkles IDE");
    return attrs;
  }

  public JarCreator(String input, String output) {
    this(input, output, getDefaultAttributes());
  }

  public JarCreator(String input, String output, Attributes attributes) {
    if (input == null || input.isBlank() || output == null || output.isBlank()) {
      throw new IllegalArgumentException("Input and output paths must be non-null and non-empty.");
    }
    this.input = input;
    this.output = output;
    this.attributes = attributes != null ? attributes : getDefaultAttributes();
  }

  public void create() throws IOException {
    var classesFile = new File(input);
    if (!classesFile.exists() || !classesFile.isDirectory()) {
      throw new IllegalArgumentException("Input path must be a valid directory.");
    }

    try (var fileOutputStream = new FileOutputStream(new File(output));
        var jarOutputStream = new JarOutputStream(fileOutputStream, createManifest(attributes))) {
      for (var file : classesFile.listFiles()) {
        addFileToJar(classesFile.getPath(), file, jarOutputStream);
      }
    }
  }

  private Manifest createManifest(final Attributes options) {
    var manifest = new Manifest();
    manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
    if (options != null) {
      manifest.getMainAttributes().putAll(options);
    }
    return manifest;
  }

  private void addFileToJar(
      final String parentPath, final File source, final JarOutputStream target) throws IOException {
    var name = source.getPath().substring(parentPath.length() + 1).replace("\\", "/");
    if (source.isDirectory()) {
      handleDirectory(name, source, target, parentPath);
    } else {
      handleFile(name, source, target);
    }
  }

  private void handleDirectory(String name, File source, JarOutputStream target, String parentPath)
      throws IOException {
    if (!name.isEmpty() && !name.endsWith("/")) {
      name += "/";
    }
    var entry = new JarEntry(name);
    entry.setTime(source.lastModified());
    target.putNextEntry(entry);
    target.closeEntry();
    for (var nestedFile : source.listFiles()) {
      addFileToJar(parentPath, nestedFile, target);
    }
  }

  private void handleFile(String name, File source, JarOutputStream target) throws IOException {
    var entry = new JarEntry(name);
    entry.setTime(source.lastModified());
    target.putNextEntry(entry);
    try (var in = new BufferedInputStream(new FileInputStream(source))) {
      byte[] buffer = new byte[1024];
      int count;
      while ((count = in.read(buffer)) != -1) {
        target.write(buffer, 0, count);
      }
    }
    target.closeEntry();
  }
}
