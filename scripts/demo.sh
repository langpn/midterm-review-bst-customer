#!/bin/bash
PROJECT_DIR="/Users/langpn/workspace/school/lap-trinh-trong-cntt-voi-java/midterm-review-bst-customer"
cd "$PROJECT_DIR"

bash scripts/compile.sh
echo ""
echo "=== KHOI CHAY DEMO TU DONG 9 CAU DE CHUP ANH BAO CAO ==="
java -Dfile.encoding=UTF-8 -cp out Main --demo
