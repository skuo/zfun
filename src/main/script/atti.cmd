tlog-listen -m 224.1.11.111 -p 2111

export LD_LIBRARY_PATH="/home/t/lib"
/sbin/ldconfig /home/t/lib

lwes-event-printing-listener -m 224.1.11.111 -p 2111

cd proj_atti
scp ypad/adsvc/ear/target/astroman-ear.ear @gratopia-dev.np.wc1.yellowpages.com:/data/.

# to add a user onto vm
tpkg -i user-skuo
tpkg -i sudo-skuo

# tdbjump
tdbjump.prod.wc1.yellowpages.com
cat .ssh/id_dsa.pub | ssh skuo@prod230.wc1.yellowpages.com 'cat >> /home/skuo/.ssh/authorized_keys2'

# lwes dejournaller
http://maven.corp.atti.com:9999/nexus/content/repositories/thirdparty/org/lwes/dejournaller/1.0.0/dejournaller-1.0.0.jar
java -jar dejournaller-1.0.0.jar -f filename -g
java -jar dejournaller-1.0.0.jar -f filename.gz -g > filename

# lwes related java projects
svn co https://lwes.svn.sourceforge.net/svnroot/lwes/lwes-journaller-java/trunk lwes-journaller-java-trunk

# mogwai download
http://mogwai:7700/log.lwes.primary.test?prefix=20110504
wget http://mogwai:7700/log.lwes.primary.test/201105041630-gratdb-dev.np.wc1.gz -O 201105041630-gratdb-dev.np.wc1.gz

# specify a base for tpkg
tpkg --base=/home/adsvc --qa

# chatzilla
/server irc.flight.yellowpages.com
/join #release

# hadoop clusters (test, staging)
http://wiki.yellowpages.com/display/FND/GRID#

test:		skynet.wc1 (np133)
staging:	hd531.ev1
		motherload-stg.np.ev1
		adsvc-stg.np.ev1
qa:		hd574.ev1
production:	adsvc.prod.ev1.yellowpages.com
		qbert.ev1
		pong.ev1
		tron.ev1
		# project specific entry nodes
  		wolfpack-grid.prod.wc1
		wc-converter.prod.ev1 

# tpkg
tpkg --start=tdb_db --init-cmd=stop-kiwi