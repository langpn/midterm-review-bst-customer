#!/bin/bash
set -e
PROJECT_DIR="/Users/langpn/workspace/school/lap-trinh-trong-cntt-voi-java/midterm-review-bst-customer"
cd "$PROJECT_DIR"

echo "=== DANG BIEN DICH DU AN JAVA 17 THUAN ==="
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
echo "Bien dich thanh cong vao thu muc out/."
