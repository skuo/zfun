# update OS
yum update

yum install emacs

# check running service
/sbin/chkconfig --list |grep :on

/sbin/chkconfig avahi-daemon off

# check hard disk performance
sudo /sbin/hdparm -t /dev/mapper/VolGroup00-LogVol00

# add user
sudo useradd <username>
sudo passwd <username>

# firewall
sudo more /etc/sysconfig/iptables

System > Administration > Security Level and Firewall

# host, hostname, network
more /etc/sysconfig/network
host `hostname`
more /etc/resolv.conf