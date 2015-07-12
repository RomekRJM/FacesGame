__author__ = 'roman'

import os
import urllib
import re

def list_urls():
    map = {}
    with open("image_links.txt") as f:
        content = f.readlines()

    for line in content:
        if "," in line:
            line_splitted = line.split(",")
            file_name_on_wiki = line_splitted[0]
            file_path_on_disk = line_splitted[1]

            if file_name_on_wiki.endswith("."):
                line_splitted[0] += "jpg"

            map[line_splitted[0]] = file_path_on_disk.rstrip()

    return map


def get_filepaths(directory):
    file_paths = []

    for root, directories, files in os.walk(directory):
        for filename in files:
            filepath = os.path.join(root, filename)
            file_paths.append(filepath)

    return file_paths


def get_license(url):
    try:
        contents = urllib.urlopen(url).read()
    except:
        contents = "No file by this name exists."

    if "No file by this name exists." in contents:
        return None
    elif "//en.wikipedia.org/wiki/en:GNU_Free_Documentation_License" in contents:
        return "GNU Free Documentation License"
    elif "//creativecommons.org/licenses/by/2.0/deed.en" in contents:
        return "Creative Commons Attribution 2.0 Generic"
    elif "//creativecommons.org/licenses/by-sa/3.0/deed.en" in contents:
        return "Creative Commons Attribution-Share Alike 3.0 Unported"
    elif "//creativecommons.org/licenses/by-sa/4.0/deed.en" in contents:
        return "Creative Commons Attribution-Share Alike 4.0 International"
    elif "//creativecommons.org/licenses/by/3.0/deed.en" in contents:
        return "Creative Commons Attribution 3.0 Unported"
    elif "//creativecommons.org/licenses/by-sa/3.0" in contents:
        return "Creative Commons Attribution-Share Alike 3.0"
    elif "https://upload.wikimedia.org/wikipedia/commons/thumb/6/62/PD-icon.svg" in contents\
            or "//en.wikipedia.org/wiki/en:public_domain" in contents\
            or "//en.wikipedia.org/wiki/public_domain" in contents\
            or "//en.wikipedia.org/wiki/Public_domain" in contents\
            or "/wiki/Public_domain" in contents:
        return "Public Domain"
    elif "//creativecommons.org/licenses/by-sa/2.5/deed.en" in contents:
        return "Creative Commons Attribution-Share Alike 2.5 Generic"
    elif "//creativecommons.org/licenses/by-sa/2.0/deed.en" in contents:
        return "Creative Commons Attribution-Share Alike 2.0 Generic"
    elif "Green_copyright.svg" in contents:
        return "The copyright holder of this file allows anyone to use it for any purpose, " \
               "provided that the copyright holder is properly attributed. Redistribution, " \
               "derivative work, commercial use, and all other use is permitted."
    elif "//creativecommons.org/licenses/by/1.0/deed.en" in contents:
        return "Creative Commons Attribution 1.0 Generic"
    elif "//en.wikipedia.org/wiki/en:Free_Art_License" in contents:
        return "Free Art License"
    elif "//creativecommons.org/licenses/by/2.5/deed.en" in contents:
        return "Creative Commons Attribution 2.5 Generic"

    return "unknown license"


def get_license_and_populate_links(image_on_wiki, filepath, links):
    license_base_url = "https://commons.wikimedia.org/wiki/File:"

    url = encode_url_idna(license_base_url, image_on_wiki)
    license = get_license(url)

    if license == None:
        return None

    links[filepath] = {
        "url": url,
        "license": license
    }

    return license


def encode_url_idna(base, file):
    try:
        url = base + file.encode('idna')
    except Exception as e:
        url = base + file

    return url


def check_person_page_on_wiki(file_name_decoded):
    # if "Bassong" in file_name_decoded:
    #     pass
    prefix = "https://en.wikipedia.org/wiki/"
    person = file_name_decoded.replace(".jpg", "").replace(".JPG", "")
    person = urllib.quote(person.encode('utf8'))
    article_url = encode_url_idna(prefix, person)

    content = urllib.urlopen(article_url).read()
    content_lines = content.split('\n')

    for line in content_lines:
        if "wgGatherPageImageThumbnail" in line:
            thumbnail_url = re.search('wgGatherPageImageThumbnail":"([^"]*)"', line).group(1)
            thumbnail_url_splitted = thumbnail_url.split("/")
            image_name = thumbnail_url_splitted[len(thumbnail_url_splitted)-2]
            return image_name

    return None


urls = list_urls()
filepaths = get_filepaths('../app/src/main/assets/photos')
links = {}
missing = 0

for filepath in filepaths:
    link = urllib.unquote_plus(filepath).decode('utf8')
    file_path_splitted = link.split("/")
    file_name_decoded = file_path_splitted[len(file_path_splitted) - 1]
    license = 'unknown'

    try:
        image_url = urls[file_name_decoded]
        image_url_splited = image_url.split("/")
        image_name = image_url_splited[len(image_url_splited)-2]
        license = get_license_and_populate_links(image_name, filepath, links)
    except KeyError as e:
        if "px-" in file_name_decoded:
            file_name_decoded = file_name_decoded.split("px-")[1]
        file_name_decoded = file_name_decoded.replace(" ", "_")
        license = get_license_and_populate_links(file_name_decoded, filepath, links)
    except Exception as e:
        license = None

    try:
        image_name = check_person_page_on_wiki(file_name_decoded)
        if image_name == None:
            raise Exception('No such page')

        license = get_license_and_populate_links(image_name, filepath, links)
    except Exception as e:
        license = None

    if license == None:
        missing += 1
        links[filepath] = {
            "url": str(missing),
            "license": str(missing)
        }

    pass

print missing

begining = """<html>
  <head></head>
  <body>
    <table>
    <tr>
      <th>No.</th>
      <th>Image</th>
      <th>License</th>
    <tr>
"""

end = """
    </table>
  </body>
</html>
"""

no = 1

with open("image_links.html", "w") as f:
    f.write(begining)
    for link in links:
        link_val = links[link]
        f.write("\n    <tr>")
        f.write("\n      <td>{0}</td>".format(no))
        f.write("\n      <td><img src='{0}'/></td>".format(link))
        f.write("\n      <td><a href='{0}'>{1}</a></td>".format(link_val['url'], link_val['license']))
        f.write("\n    </tr>")
        no = no + 1
    f.write(end)
pass