# spring-tls
Spring boot withTLS and HTTP/2


## Certificate for ROOT-CA
Created two subdirs: `root-ca` and `server`.

Creating root certificate
``` 
$ keytool -genkeypair -keyalg RSA -keysize 3072 -alias root-ca -dname "CN=My Root CA,OU=Development,O=BOFT,C=DE" -ext BC:c=ca:true -ext KU=keyCertSign -validity 3650 -keystore ./root-ca/ca.jks -storepass secret -keypass secret
```
This command creates a new java keystore ca.jks in folder root-ca containing the private and public keys. The certificate uses the RSA algorithm with a bit length of 3072 and is valid for 10 years

Exporting the certificate to file ca.pem in the subdirectory root-ca 
```
$ keytool -exportcert -keystore ./root-ca/ca.jks -storepass secret -alias root-ca -rfc -file ./root-ca/ca.pem
```

## Signed Server Certificate

Creating key store file containing the private/public keys for the server certificate.
```
keytool -genkeypair -keyalg RSA -keysize 3072 -alias localhost -dname "CN=localhost,OU=Development,O=BOFT,C=DE" -ext BC:c=ca:false -ext EKU:c=serverAuth -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -validity 3650 -keystore ./server/server.jks -storepass secret -keypass secret
```
Here used the RSA algorithm with bit length of 3072 and set it valid for 10 years.

Generation of the signing request for your server certificate. This creates the file server.csr in sub directory server.
```
keytool -certreq -keystore ./server/server.jks -storepass secret -alias localhost -keypass secret -file ./server/server.csr
```

Signing and exporting server certificate using the file server.csr.
```
keytool -gencert -keystore ./root-ca/ca.jks -storepass secret -infile ./server/server.csr -alias root-ca -keypass secret -ext BC:c=ca:false -ext EKU:c=serverAuth -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -validity 3650 -rfc -outfile ./server/server.pem
```

Add certificate to key store (root ca)
```
keytool -importcert -noprompt -keystore ./server/server.jks -storepass secret -alias root-ca -keypass secret -file ./root-ca/ca.pem
keytool -importcert -noprompt -keystore ./server/server.jks -storepass secret -alias localhost -keypass secret -file  ./server/server.pem
```

