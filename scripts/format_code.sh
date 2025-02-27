#!/bin/bash

# Copyright 2025 Aquiles Trindade (trindadedev).
# Licensed under the Apache 2.0 License.

# Variables

# Directory where files to be formatted are located
TO_FORMAT_DIR="./"

CACHE_DIR="$HOME/.cache/trindadedev/formatters"

# Java Formatter Vars
JAVA_FORMATTER_DIR="$CACHE_DIR/google-java-format.jar"
JAVA_FORMATTER_VERSION="1.25.2"
JAVA_FORMATTER_URL="https://github.com/google/google-java-format/releases/download/v$JAVA_FORMATTER_VERSION/google-java-format-$JAVA_FORMATTER_VERSION-all-deps.jar"

# XML Formatter Vars
XML_FORMATTER_DIR="$CACHE_DIR/android-xml-formatter.jar"
XML_FORMATTER_VERSION="1.1.1"
XML_FORMATTER_URL="https://github.com/teixeira0x/android-xml-formatter/releases/download/v$XML_FORMATTER_VERSION/android-xml-formatter.jar"

# Kotlin Formatter Vars
KOTLIN_FORMATTER_DIR="$CACHE_DIR/ktfmt.jar"
KOTLIN_FORMATTER_VERSION="0.53"
KOTLIN_FORMATTER_URL="https://github.com/facebook/ktfmt/releases/download/v$KOTLIN_FORMATTER_VERSION/ktfmt-$KOTLIN_FORMATTER_VERSION-jar-with-dependencies.jar"

mkdir -p "$CACHE_DIR"

# download a formatter if not exists
download_formatter_if_not_present() {
  local formatter_dir="$1"
  local formatter_url="$2"
  local formatter_name="$3"

  if [ ! -f "$formatter_dir" ]; then
    echo "Downloading $formatter_name..."
    wget -q "$formatter_url" -O "$formatter_dir"
  fi
}

# format Java files
format_java() {
  read -p "Do you want to format all Java files in this directory and subdirectories? (Y/N): " answer
  case "$answer" in
    [Yy]* )
      download_formatter_if_not_present "$JAVA_FORMATTER_DIR" "$JAVA_FORMATTER_URL" "Google Java Formatter"
      echo "Formatting Java files..."
      find "$TO_FORMAT_DIR" -name "*.java" -exec java -jar "$JAVA_FORMATTER_DIR" --replace {} +
      ;;
    [Nn]* ) ;;
    * ) echo "Invalid option. Exiting."; exit 1 ;;
  esac
}

# format XML files
format_xml() {
  read -p "Do you want to format all XML files in this directory and subdirectories? (Y/N): " answer
  case "$answer" in
    [Yy]* )
      download_formatter_if_not_present "$XML_FORMATTER_DIR" "$XML_FORMATTER_URL" "Android XML Formatter"
      echo "Formatting XML files..."
      find "$TO_FORMAT_DIR" -name "*.xml" -exec java -jar "$XML_FORMATTER_DIR" --indention 4 --attribute-indention 4 {} \;
      ;;
    [Nn]* ) ;;
    * ) echo "Invalid option. Exiting."; exit 1 ;;
  esac
}

# format Kotlin files
format_kotlin() {
  read -p "Do you want to format all Kotlin files in this directory and subdirectories? (Y/N): " answer
  case "$answer" in
    [Yy]* )
      download_formatter_if_not_present "$KOTLIN_FORMATTER_DIR" "$KOTLIN_FORMATTER_URL" "Facebook KtFmt"
      echo "Formatting Kotlin files..."
      find "$TO_FORMAT_DIR" -name "*.kt" -exec java -jar "$KOTLIN_FORMATTER_DIR" --google-style {} +
      ;;
    [Nn]* ) ;;
    * ) echo "Invalid option. Exiting."; exit 1 ;;
  esac
}

# download a package verifing the environment
download_package() {
  local package = "$1"
  if command -v pkg &> /dev/null; then
    pkg update -y && pkg install -y $package
  elif command -v apt &> /dev/null; then
    sudo apt update && sudo apt install -y $package
  elif command -v pacman &> /dev/null; then
    sudo pacman -Syu --noconfirm $package
  elif command -v dnf &> /dev/null; then
    sudo dnf install -y $package
  elif command -v yum &> /dev/null; then
    sudo yum install -y $package
  elif command -v brew &> /dev/null; then
    brew install $package
  else
    echo "Not supported package manager"
    exit 1
  fi
}

# call all methods for formatter
format_java
format_xml
format_kotlin
