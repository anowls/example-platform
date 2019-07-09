#!/usr/bin/env bash
# 在当前目录下搜索以 docker 开头的文件
find -name docker* 
# 在根目录下搜索以 docker 开头的文件
find / -name docker*
# 在根目录下搜索以 docker 开头的文件, 并把结果输入到名为 file 的文件中
find / -name docker* -fprint file
# 在根目录下搜索大于512k的文件
find / -size +512k
# 在根目录下搜索小于512k的文件
find / -size -512k
# 在根目录下搜索大小为0的文件
find / -empty
# 在根目录下搜索最近24小时访问过的文件
find / -atime -1 
# 在根目录下搜索最近24小时修改过的文件
find / -mtime -1
# 在根目录下搜索最近1分钟访问过的文件
find / -amin -1
# 在根目录下搜索最近1分钟修改过的文件
find / -mmin -1
