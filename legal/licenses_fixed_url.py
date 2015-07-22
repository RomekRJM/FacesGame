__author__ = 'roman'

import os
import re
import urllib

def get_wiki_image(wiki_link):

    if 'http' not in wiki_link:
        return ''

    content = urllib.urlopen(wiki_link).read()
    match = None

    try:
        match = re.search('href="([^"]*)" class="mw-thumbnail-link"', content).group(1)
    except Exception as e:
        match = None

    try:
        if not match:
            match = re.search('"fullImageLink" id="file"><a href="([^"]*)"', content).group(1)
    except Exception as e:
        print wiki_link

    if not match:
        return ''

    return 'https:' + match


try:
    os.remove('image_licenses.html')
except Exception as e:
    pass

with open('image_links.html', "r") as f:
    content = f.readlines()

new_content = []
path = None
link = None

for line in content:

    if 'img src' in line:
        match = re.search("'(.*)'", line)

        if match:
            path = match.group(1)

        new_line = None

    elif '<a href=' in line:
        match = re.search("'(.*)'", line)

        if match:
            link = match.group(1)

        new_line = "      <td><img src='" + get_wiki_image(link) + "'/></td>\n" + line

    elif '</tr>' in line:
        if path:
            new_line = '      <td>' + path + '</td>\n    </tr>\n'
        else:
            new_line = '      <th>Path in app</th>\n    </tr>\n'

    else:
        new_line = line

    if new_line:
        new_content.append(new_line)



with open("image_licenses.html", "w+") as f:
    for line in new_content:
        f.write(line)
pass