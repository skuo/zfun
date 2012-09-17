/usr/NX/bin/nxserver --status|--start|--stop|--restart

Server keys

The initial login between client and server happens through a DSA key 
pair, i.e. a couple of specially generated cryptographic keys, called 
the private key and the public key, which allow you to establish a 
secure connection, by means of SSL encryption, between NX client and 
NX server. 

The public part of the key-pair is provided during the installation 
of the server, while the private part of the key-pair is distributed 
together with the NX Client. This ensures that each NX client is able 
to authenticate to the server and to start the procedure for autho-
rizing the user and negotiating the session. 

If you want to create a virtual private network (VPN) instead, you 
need to generate a new DSA key-pair and distribute the private part 
of the key-pair to those NX clients you want authenticated to the NX 
server. More information on how to generate and distribute a new DSA 
key-pair is available at:  

http://www.nomachine.com/ar/view.php?ar_id=AR01C00126

Creating Users

NX is configured to allow access from any system user, as long as 
valid credentials are given to the user for the SSH login. NX pro-
vides an alternative authorization method, allowing system admin-
istrators to determine which users are given access to the NX fun-
ctionalities. This works by implementing a separation between the 
system password and the NX password, so that, for example, it is 
possible to forbid remote access to the system by any other means 
except via NX and use the NX tools to implement effective accounting 
of the system resources used by the user, or to share NX passwords in 
an external database.

To activate the NX user and password DBs, you will have to edit the
NX server configuration file by hand or use the NX Server Manager 
Web tool available for download on the NoMachine Web site at:

http://www.nomachine.com/download-manager.php

Session Shadowing and Desktop Sharing

The session shadowing functionality allows you to share NX sessions 
running on the node. The desktop sharing functionality instead, gives 
access to the native display of the X server as if you were in front 
of the monitor. By default you can access sessions in interactive mode 
and upon authorization of the session owner. You can modify this beha-
viour by tuning the server configuration according to your needs, for 
example by allowing access to sessions in view-only mode, or connecting
to either a suspended session or the local display via the Desktop 
Manager login window.

Load Balancing

NX Advanced Server provides support for multi-node capabilities and 
load balancing. In its current implementation, NX server can only 
manage accounts on the host machine, so to grant access to the node 
running remotely, you will need to create the user account directly 
on the remote node host by issuing the NX node commands as root user.
You will also need to add the NX Server public DSA Key to the node to 
allow this server to connect to the node running on the remote host. 

Documentation

For further information on how to manage the configuration of your 
NX system, please refer to the System Administrator's Guide available 
on the NoMachine Web site at:

http://www.nomachine.com/documentation/admin-guide.php

The NoMachine Team.
