##
find . -name "*.ear" -exec ls -l {} \;

## debug shell script
bash -x test_data_extractor.sh

#!/bin/bash -xv

# find CentOS version
cat /etc/redhat-release

# reboot 
/sbin/shutdown -r now

# rsa key
ssh-keygen -t dsa
cat .ssh/id_dsa.pub | ssh skuo@prod230.wc1.yellowpages.com 'cat >> /home/skuo/.ssh/authorized_keys2'

# 
curl http://prod263.wc1:8898/upload
# get from chip
curl "http://qa-adsvc-chip1.np.wc1.yellowpages.com:8889/chip-web/bids/sourceId/source/wp"
# post data/file to chip
curl -d '{"source":"wp","bid":5.222,"sourceId":"sourceId","updatedAt":20111104175336}' http://qa-adsvc-chip1.np.wc1.yellowpages.com:8889/chip-web/bids/sourceId/source/wp
curl -F "fileupload=@filename.txt" http://qa-adsvc-chip1.np.wc1.yellowpages.com:8889/chip-web/bids/sourceId/source/wp
# put data to chip
curl -X PUT -d '{"source":"wp","bid":1.222,"sourceId":"sourceId","updatedAt":20111104175336}' http://qa-adsvc-chip1.np.wc1.yellowpages.com:8889/chip-web/bids/sourceId/source/wp -H "Content-Type:application/json"
# 
# -v for verbose to see response header
# -i include headers
# curl to CAS
# send Content-type,multipart/form-data
curl -v -i -F username=username -F password=password https://sso.attinteractive.com/cas/login.txt?service=http://grabds.np.wc1.yellowpages.com:8889/bds-web/filesync
# send json payload
curl -v -i --header "Content-Type:application/json" --header "Accept:application/json" --data '{"username":"bds","password":"12astroman"}' https://ssoqa.attinteractive.com/cas/login.json?service=http://grabds.np.wc1.yellowpages.com:8889/bds-web/filesync
# receive xml payload
curl -v -i --header "Content-Type:application/json" --header "Accept:text/xml" --data '{"username":"username","password":"password"}' https://ssoqa.attinteractive.com/cas/login.xml?service=http://grabds.np.wc1.yellowpages.com:8889/bds-web/filesync
# send Content-type,multipart/form-data
curl -v -i --header "Content-Type:multipart/form-data" --header "Accept:text/xml" --data 'username=username&password=password' https://sso.attinteractive.com/cas/login.xml?service=http://grabds.np.wc1.yellowpages.com:8889/bds-web/filesync

# grep with filename
find . -name "*.sh" -exec grep -Hn hello {} \;
# grep with regex
grep -P "pid=[^ &]" 2011080100-prod282.wc1 
# grep to print text from binary file
grep --text loading
# grep negative number
grep '\-1,' file
# grep multiple options, egrep = 'grep -E'
egrep "\"Name\"|\"City\"|\"State\"|\"HeadingTest\"
  "Name": "Park Tavern",
  "City": "Atlanta",
  "HeadingText": "Brew Pubs",
  "State": "GA",

egrep 'DIFF|Thres' /tmp/AdSelUtil.log
# performance
vmstat
iostat
top
mpstat
hdparm

# copy without .svn files
cp -r src dest
find dest -name '.svn' -exec rm -r {} \;

# openssl
openssl enc -d -aes-256-cbc -in chip-db.properties.enc -out chip-db.properties -pass "pass:$passwd"
openssl enc -e -aes-256-cbc -in chip-db.properties -out chip-db.properties.enc -pass "pass:$passwd"
# to stdout
openssl enc -e -aes-256-cbc -in chip-db.properties.enc -pass "pass:$passwd"

# sort
# set SEP to "," and sort on field 4 and 5
sort -t , -k4,5 ltr.tmp