#!/bin/bash
PROJECT_DIR="/Users/langpn/workspace/school/lap-trinh-trong-cntt-voi-java/midterm-review-bst-customer"
cd "$PROJECT_DIR"

if [ ! -d "out" ] || [ -z "$(ls -A out)" ]; then
    echo "Chua tim thay bytecode. Dang bien dich..."
    bash scripts/compile.sh
fi

java -Dfile.encoding=UTF-8 -cp out Main
