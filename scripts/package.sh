#!/bin/bash
PROJECT_DIR="/Users/langpn/workspace/school/lap-trinh-trong-cntt-voi-java/midterm-review-bst-customer"
cd "$PROJECT_DIR"

ZIP_NAME="25765251_PhamNgocLang_BTTL_Java_BST_Customer.zip"
echo "=== DANG DONG GOI MA NGUON NOP CHO THAY DO HA PHUONG ==="
rm -f "$ZIP_NAME"

zip -r "$ZIP_NAME" src data scripts screenshots README.md BAO_CAO.md BAO_CAO.docx 25765251_PhamNgocLang_BAO_CAO_BST_Customer.docx .gitignore -x "*.DS_Store" -x "out/*" -x "screenshots_raw/*"

echo "Dong goi thanh cong: $PROJECT_DIR/$ZIP_NAME"
ls -lh "$ZIP_NAME"
