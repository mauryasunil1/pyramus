#!/usr/bin/env python
import sys
import os
import os.path
import xml.etree.ElementTree as ET

ns = 'http://maven.apache.org/SETTINGS/1.0.0'

if (("OOM2_USER" in os.environ) and ("OOM2_PASSWORD" in os.environ)):
  homedir = os.path.expanduser("~")

  ET.register_namespace('', ns)
  m2 = ET.parse(homedir + '/.m2/settings.xml')
  settings = m2.getroot()
  servers = settings.find('s:servers', {'s': ns})
  if servers is None:
      servers = ET.SubElement(settings, 'servers')

  oo_snapshots = ET.SubElement(servers, 'server')
  ET.SubElement(oo_snapshots, 'id').text = 'otavanopisto-snapshots'
  ET.SubElement(oo_snapshots, 'username').text = os.environ['OOM2_USER']
  ET.SubElement(oo_snapshots, 'password').text = os.environ['OOM2_PASSWORD']
  
  oo_releases = ET.SubElement(servers, 'server')
  ET.SubElement(oo_releases, 'id').text = 'otavanopisto-releases'
  ET.SubElement(oo_releases, 'username').text = os.environ['OOM2_USER']
  ET.SubElement(oo_releases, 'password').text = os.environ['OOM2_PASSWORD']

  mirrors = settings.find('s:mirrors', {'s': ns})
  if mirrors is None:
      mirrors = ET.SubElement(settings, 'mirrors')

  oo_snapshots_mirror = ET.SubElement(mirrors, 'mirror')
  ET.SubElement(oo_snapshots_mirror, 'id').text = 'otavanopisto-snapshots-http-unblocker'
  ET.SubElement(oo_snapshots_mirror, 'mirrorOf').text = 'otavanopisto-snapshots'
  ET.SubElement(oo_snapshots_mirror, 'url').text = 'http://nexus.muikkuverkko.fi:8081/repository/otavanopisto-snapshots/'
  ET.SubElement(oo_snapshots_mirror, 'blocked').text = 'false'  

  oo_releases_mirror = ET.SubElement(mirrors, 'mirror')
  ET.SubElement(oo_releases_mirror, 'id').text = 'otavanopisto-releases-http-unblocker'
  ET.SubElement(oo_releases_mirror, 'mirrorOf').text = 'otavanopisto-releases'
  ET.SubElement(oo_releases_mirror, 'url').text = 'http://nexus.muikkuverkko.fi:8081/repository/otavanopisto-releases/'
  ET.SubElement(oo_releases_mirror, 'blocked').text = 'false'

  m2.write(homedir + '/.m2/mySettings.xml',
          xml_declaration = True,
          encoding = 'utf-8',
          method = 'xml')
else:
 print("no secure environment variables available, skipping deployment")
 sys.exit()
