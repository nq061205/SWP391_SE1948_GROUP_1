#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script to remove comments from Java and JSP files
Removes: HTML comments, JSP comments, Java single-line and multi-line comments
"""

import re
import os
import sys

def remove_comments_from_content(content, file_extension):
    """Remove various types of comments from file content"""
    original_length = len(content)
    
    # Remove JSP comments <%-- --%>
    if file_extension == '.jsp':
        content = re.sub(r'<%--[\s\S]*?--%>', '', content)
    
    # Remove HTML comments <!-- -->
    content = re.sub(r'<!--[\s\S]*?-->', '', content)
    
    # Remove multi-line Java/JSP comments /* */
    content = re.sub(r'/\*[\s\S]*?\*/', '', content)
    
    # Remove single-line comments // but preserve URLs
    lines = content.split('\n')
    processed_lines = []
    
    for line in lines:
        # Check if line contains URL patterns - if so, keep as is
        if 'http://' in line or 'https://' in line or 'file://' in line:
            processed_lines.append(line)
            continue
        
        # Find // outside of strings
        in_string = False
        string_char = None
        comment_pos = -1
        
        i = 0
        while i < len(line):
            char = line[i]
            
            # Track string boundaries
            if char in ['"', "'"] and (i == 0 or line[i-1] != '\\'):
                if in_string and char == string_char:
                    in_string = False
                    string_char = None
                elif not in_string:
                    in_string = True
                    string_char = char
            
            # Find comment start outside strings
            if not in_string and i < len(line) - 1:
                if line[i:i+2] == '//':
                    comment_pos = i
                    break
            
            i += 1
        
        # Remove comment if found
        if comment_pos >= 0:
            line = line[:comment_pos].rstrip()
        
        processed_lines.append(line)
    
    content = '\n'.join(processed_lines)
    
    # Remove lines that are just "/* Lines X-Y omitted */"
    content = re.sub(r'^\s*/\*\s*Lines?\s+\d+(-\d+)?\s+omitted\s*\*/\s*$', '', content, flags=re.MULTILINE)
    
    # Clean up excessive blank lines (more than 2 consecutive)
    content = re.sub(r'\n{3,}', '\n\n', content)
    
    # Remove trailing whitespace from each line
    lines = content.split('\n')
    lines = [line.rstrip() for line in lines]
    content = '\n'.join(lines)
    
    removed_bytes = original_length - len(content)
    percentage = (removed_bytes / original_length * 100) if original_length > 0 else 0
    
    return content, removed_bytes, percentage

def process_file(file_path, dry_run=False):
    """Process a single file to remove comments"""
    print(f"\n{'='*60}")
    print(f"Processing: {file_path}")
    print(f"{'='*60}")
    
    if not os.path.exists(file_path):
        print(f"❌ File not found: {file_path}")
        return False
    
    # Read file
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            original_content = f.read()
    except Exception as e:
        print(f"❌ Error reading file: {e}")
        return False
    
    if not original_content:
        print("⚠️  File is empty, skipping")
        return True
    
    # Get file extension
    _, ext = os.path.splitext(file_path)
    
    # Remove comments
    new_content, removed_bytes, percentage = remove_comments_from_content(original_content, ext)
    
    if original_content == new_content:
        print("✓ No comments found or no changes needed")
        return True
    
    print(f"📊 Statistics:")
    print(f"   - Original size: {len(original_content):,} bytes")
    print(f"   - New size: {len(new_content):,} bytes")
    print(f"   - Removed: {removed_bytes:,} bytes ({percentage:.2f}%)")
    
    if dry_run:
        print("🔍 DRY RUN - No changes saved")
        return True
    
    # Write back to file
    try:
        with open(file_path, 'w', encoding='utf-8', newline='') as f:
            f.write(new_content)
        print("✓ File saved successfully")
        return True
    except Exception as e:
        print(f"❌ Error writing file: {e}")
        return False

def main():
    """Main function"""
    print("╔" + "="*58 + "╗")
    print("║" + " "*15 + "COMMENT REMOVAL SCRIPT" + " "*21 + "║")
    print("╚" + "="*58 + "╝")
    
    # Check for dry run flag
    dry_run = '--dry-run' in sys.argv or '-d' in sys.argv
    
    if dry_run:
        print("\n🔍 Running in DRY RUN mode (no files will be modified)\n")
    
    # List of files to process
    files = [
        "src/main/webapp/Views/joblist.jsp",
        "src/main/webapp/Views/uploadedPost.jsp",
        "src/main/webapp/Views/HR/recruitmentManagement.jsp",
        "src/main/webapp/Views/HRM/ManagePost.jsp",
        "src/main/webapp/Views/HRM/PostReview.jsp",
        "src/main/webapp/Views/HRM/PostReviewDetail.jsp",
        "src/main/java/controller/hrm/ManagePostServlet.java",
        "src/main/java/controller/hrm/HRManagerRecruitmentServlet.java",
        "src/main/java/controller/hr/HRRecruitmentServlet.java",
        "src/main/java/controller/JobSiteServlet.java"
    ]
    
    # Get script directory
    script_dir = os.path.dirname(os.path.abspath(__file__))
    
    # Process each file
    success_count = 0
    error_count = 0
    
    for file in files:
        file_path = os.path.join(script_dir, file)
        if process_file(file_path, dry_run):
            success_count += 1
        else:
            error_count += 1
    
    # Summary
    print("\n" + "="*60)
    print("SUMMARY")
    print("="*60)
    print(f"✓ Successfully processed: {success_count} files")
    if error_count > 0:
        print(f"❌ Errors: {error_count} files")
    if dry_run:
        print("🔍 Mode: DRY RUN (no changes saved)")
    else:
        print("💾 Mode: LIVE (changes saved)")
    print("="*60)
    print("\n✨ Done!\n")

if __name__ == "__main__":
    main()
