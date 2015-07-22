__author__ = 'roman'

import os;
import re

try:
    os.remove('image_licenses.html')
except Exception as e:
    pass

content = None

with open('image_links.html', "r") as f:
    content = f.readlines()

new_content = []
path = None

for line in content:

    if 'img src' in line:
        match = re.search("'(.*)'", line)

        if match:
            path = match.group(0)

        new_line = line

    elif '</tr>' in line:
        if path:
            new_line = '      <td>' + path + '</td>\n    </tr>\n'
        else:
            new_line = '      <th>Path in app</th>\n    </tr>\n'

    else:
        new_line = line

    new_content.append(new_line)



with open("image_licenses.html", "w+") as f:
    for line in new_content:
        f.write(line)
pass
