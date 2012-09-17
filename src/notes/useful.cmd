#
# -v for verbose to see response header
# -i include headers
# curl to CAS
# send Content-type,multipart/form-data
curl -v -i -F username=username -F password=password https://sso.attinteractive.com/cas/login.txt?service=http://grabds.np.wc1.yellowpages.com/bds-web/filesync
# send json payload
curl -v -i --header "Content-Type:application/json" --header "Accept:application/json" --data '{"username":"username","password":"password"}' https://ssoqa.attinteractive.com/cas/login.json?service=http://grabds.np.wc1.yellowpages.com/bds-web/filesync
# receive xml payload
curl -v -i --header "Content-Type:application/json" --header "Accept:text/xml" --data '{"username":"username","password":"password"}' https://ssoqa.attinteractive.com/cas/login.xml?service=http://grabds.np.wc1.yellowpages.com/bds-web/filesync
# send Content-type,multipart/form-data
curl -v -i --header "Content-Type:multipart/form-data" --header "Accept:text/xml" --data 'username=username&password=password' https://sso.attinteractive.com/cas/login.xml?service=http://grabds.np.wc1.yellowpages.com/bds-web/filesync

#---------------------------------
# Getting and validating ticket for grabds.np.wc1.yellowpages.com:8889/bds-web/secure/filesync
#
# return:
# yes
# bds
#---------------------------------
curl -i -F username=bds -F password=password https://sso.attinteractive.com/cas/login.txt?service=http%3A%2F%2Fgrabds.np.wc1.yellowpages.com%3A8889%2Fbds-web%2Fsecure%2Ffilesync

curl -i 'https://sso.attinteractive.com/cas/validate?ticket=ST-1330380933r211B9&service=http%3A%2F%2Fgrabds.np.wc1.yellowpages.com%3A8889%2Fbds-web%2Fsecure%2Ffilesync'

#---------------------------------
# Getting and validating ticket for 10.45.11.28:8001/bds-web/secure/fileupload
#
# return:
# yes
# bds
#---------------------------------
curl -i -F username=bds -F password=password https://sso.attinteractive.com/cas/login.txt?service=http://10.45.11.28:8001/bds-web/secure/fileupload

curl -i -F directory=mac -F "file=@tmp" 'http://10.45.11.28:8001/bds-web/secure/fileupload?ticket=ST-1330390929r2FBC8'

#-----------------------------------
# Hive: remove tab 
#-----------------------------------
regexp_replace(listing_id, "\t", " ")