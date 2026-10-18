#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Chuyển đổi BAO_CAO.md sang BAO_CAO.docx có nhúng hình ảnh chụp thực tế.
Sinh viên: Phạm Ngọc Lang | MSSV: 25765251
"""

import os
import re
from docx import Document
from docx.shared import Inches, Pt, RGBColor
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_TABLE_ALIGNMENT
from docx.oxml import OxmlElement, parse_xml
from docx.oxml.ns import nsdecls, qn

PROJECT_DIR = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
MD_PATH = os.path.join(PROJECT_DIR, "BAO_CAO.md")
DOCX_PATH = os.path.join(PROJECT_DIR, "BAO_CAO.docx")
DOCX_SUBMIT_PATH = os.path.join(PROJECT_DIR, "25765251_PhamNgocLang_BAO_CAO_BST_Customer.docx")

def set_cell_background(cell, fill_hex):
    tcPr = cell._tc.get_or_add_tcPr()
    shd = parse_xml(f'<w:shd {nsdecls("w")} w:fill="{fill_hex}"/>')
    tcPr.append(shd)

def create_docx():
    print(f"Dang doc noi dung tu: {MD_PATH}")
    with open(MD_PATH, "r", encoding="utf-8") as f:
        lines = f.readlines()

    doc = Document()

    # Thiet lap le trang A4 chuan (Top/Bottom: 2cm, Left: 2.5cm, Right: 2cm)
    for section in doc.sections:
        section.top_margin = Inches(0.8)
        section.bottom_margin = Inches(0.8)
        section.left_margin = Inches(1.0)
        section.right_margin = Inches(0.8)

    # Thiet lap Style Normal chuan font Times New Roman 12pt
    style = doc.styles['Normal']
    font = style.font
    font.name = 'Times New Roman'
    font.size = Pt(12)
    font.color.rgb = RGBColor(0x22, 0x22, 0x22)

    in_code_block = False
    code_lines = []
    code_lang = ""

    for line in lines:
        raw_line = line.rstrip('\r\n')

        # Xu ly khoi Code Block
        if raw_line.startswith("```"):
            if not in_code_block:
                in_code_block = True
                code_lang = raw_line[3:].strip()
                code_lines = []
            else:
                in_code_block = False
                # Chen bang 1 o gia lap o code block
                table = doc.add_table(rows=1, cols=1)
                table.alignment = WD_TABLE_ALIGNMENT.CENTER
                cell = table.cell(0, 0)
                set_cell_background(cell, "F5F5F5")
                cell.width = Inches(6.5)

                cp = cell.paragraphs[0]
                cp.paragraph_format.space_before = Pt(4)
                cp.paragraph_format.space_after = Pt(4)
                cp.paragraph_format.line_spacing = 1.05

                code_text = "\n".join(code_lines)
                run = cp.add_run(code_text)
                run.font.name = 'Consolas'
                run.font.size = Pt(9.5)
                run.font.color.rgb = RGBColor(0x1B, 0x36, 0x5D)

                doc.add_paragraph().paragraph_format.space_after = Pt(2)
            continue

        if in_code_block:
            code_lines.append(raw_line)
            continue

        # Xu ly anh: ![Caption](path)
        img_match = re.match(r'!\[(.*?)\]\((.*?)\)', raw_line.strip())
        if img_match:
            caption = img_match.group(1)
            img_rel_path = img_match.group(2)
            img_full_path = os.path.join(PROJECT_DIR, img_rel_path)

            if os.path.exists(img_full_path):
                p_img = doc.add_paragraph()
                p_img.alignment = WD_ALIGN_PARAGRAPH.CENTER
                p_img.paragraph_format.space_before = Pt(8)
                p_img.paragraph_format.space_after = Pt(2)
                run_img = p_img.add_run()
                # Chieu rong anh fit chuan 6.2 inches
                run_img.add_picture(img_full_path, width=Inches(6.2))

                p_cap = doc.add_paragraph()
                p_cap.alignment = WD_ALIGN_PARAGRAPH.CENTER
                p_cap.paragraph_format.space_before = Pt(2)
                p_cap.paragraph_format.space_after = Pt(8)
                run_cap = p_cap.add_run(f"Hinh: {caption}")
                run_cap.font.name = 'Times New Roman'
                run_cap.font.size = Pt(10)
                run_cap.font.italic = True
                run_cap.font.color.rgb = RGBColor(0x55, 0x55, 0x55)
            else:
                p_err = doc.add_paragraph(f"[Hinh anh khong ton tai tai: {img_rel_path}]")
                p_err.runs[0].font.color.rgb = RGBColor(0xC0, 0x00, 0x00)
            continue

        # Tieu de cap 1 (# ...)
        if raw_line.startswith("# "):
            h1 = doc.add_paragraph()
            h1.alignment = WD_ALIGN_PARAGRAPH.CENTER
            h1.paragraph_format.space_before = Pt(14)
            h1.paragraph_format.space_after = Pt(6)
            run = h1.add_run(raw_line[2:].strip())
            run.font.name = 'Times New Roman'
            run.font.size = Pt(18)
            run.font.bold = True
            run.font.color.rgb = RGBColor(0x00, 0x33, 0x66)
            continue

        # Tieu de cap 2 (## ...)
        if raw_line.startswith("## "):
            h2 = doc.add_paragraph()
            h2.paragraph_format.space_before = Pt(12)
            h2.paragraph_format.space_after = Pt(4)
            run = h2.add_run(raw_line[3:].strip())
            run.font.name = 'Times New Roman'
            run.font.size = Pt(14)
            run.font.bold = True
            run.font.color.rgb = RGBColor(0x00, 0x4D, 0x80)
            continue

        # Tieu de cap 3 (### ...)
        if raw_line.startswith("### "):
            h3 = doc.add_paragraph()
            h3.paragraph_format.space_before = Pt(10)
            h3.paragraph_format.space_after = Pt(3)
            run = h3.add_run(raw_line[4:].strip())
            run.font.name = 'Times New Roman'
            run.font.size = Pt(13)
            run.font.bold = True
            run.font.color.rgb = RGBColor(0x1B, 0x36, 0x5D)
            continue

        # Duong ke ngang ---
        if raw_line.strip() == "---":
            p = doc.add_paragraph()
            p.paragraph_format.space_before = Pt(4)
            p.paragraph_format.space_after = Pt(6)
            p_run = p.add_run("_________________________________________________________________________________")
            p_run.font.size = Pt(8)
            p_run.font.color.rgb = RGBColor(0xAA, 0xAA, 0xAA)
            continue

        # Dong trong
        if not raw_line.strip():
            continue

        # Doan van thong thuong / Danh sach bullet
        p = doc.add_paragraph()
        p.paragraph_format.space_before = Pt(0)
        p.paragraph_format.space_after = Pt(4)
        p.paragraph_format.line_spacing = 1.15

        text = raw_line
        if text.startswith("- "):
            p.paragraph_format.left_indent = Inches(0.25)
            text = text[2:].strip()
            run_bullet = p.add_run("• ")
            run_bullet.bold = True
        elif re.match(r'^\d+\.\s', text):
            p.paragraph_format.left_indent = Inches(0.25)

        # Xu ly in dam bang regex **bold**
        parts = re.split(r'(\*\*.*?\*\*)', text)
        for part in parts:
            if part.startswith("**") and part.endswith("**"):
                r = p.add_run(part[2:-2])
                r.bold = True
            else:
                # Xu ly `code` inline
                code_parts = re.split(r'(`.*?`)', part)
                for cp in code_parts:
                    if cp.startswith("`") and cp.endswith("`"):
                        rc = p.add_run(cp[1:-1])
                        rc.font.name = 'Consolas'
                        rc.font.size = Pt(10.5)
                        rc.font.color.rgb = RGBColor(0x8B, 0x00, 0x00)
                    else:
                        p.add_run(cp)

    print("Dang luu tai lieu docx...")
    doc.save(DOCX_PATH)
    doc.save(DOCX_SUBMIT_PATH)
    print(f"Xuat thanh cong 2 file docx:\n  1. {DOCX_PATH}\n  2. {DOCX_SUBMIT_PATH}")

if __name__ == "__main__":
    create_docx()
