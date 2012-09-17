# check alternative configs
sudo /usr/sbin/alternatives --display hadoop-0.20-conf

# script to start all service
$ for service in /etc/init.d/hadoop-0.20-*
> do
> sudo $service start
> done


#
Hadoop wrapper script          /usr/bin/hadoop-0.20
Hadoop Configuration Files     /etc/hadoop-0.20/conf
Hadoop Jar and Library Files   /usr/lib/hadoop-0.20
Hadoop Log Files               /var/log/hadoop-0.20
Hadoop Man pages               /usr/share/man
Hadoop service scripts         /etc/init.d/hadoop-0.20-*

/usr/lib/hadoop

#
sudo -u hdfs hadoop fs -chmod 777 /
sudo -u hdfs hadoop fs -chmod 777 /user
