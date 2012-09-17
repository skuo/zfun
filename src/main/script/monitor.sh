for host in astroman-hadoop.prod 
do
    echo "----- $host -----"
    ssh $host.ev1.yellowpages.com 'tpkg --qa | grep -P --color=always "(adsvc_hadoop|impression-position-norma
lizer|listing-quality-calculator|adsvc-budget-scoop)";'
    ssh $host.ev1.yellowpages.com 'for cron in $(ls /home/t/ypad/etc/cron.d/*.cron) 
do 
    echo "----- $cron -----"; 
    cat $cron; 
done'

done
