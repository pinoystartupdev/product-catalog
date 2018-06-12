# product-catalog
A simple test app for Carmudi
 - calls web service API, parses JSON-formatted response, displays tha data
 - adaptive to portrait and landscape
 - displays product's image, name, price and brand
 - pull to refresh
 - infinite scroll
 - support offline functionality

prepared by Cris Feraer

# IDE
Android Studio 3.1.3
Build #AI-173.4819257, built on June 5, 2018
JRE: 1.8.0_152-release-1024-b01 x86_64
JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
Mac OS X 10.13.4

# APK
compileSdkVersion 27
minSdkVersion 16
targetSdkVersion 27

# Libraries
Picasso                     : implementation 'com.squareup.picasso:picasso:2.71828'
Gson                        : implementation 'com.google.code.gson:gson:2.6.2'
Retrofit                    : implementation ('com.squareup.retrofit2:retrofit:2.1.0') {
                                  exclude module: 'okhttp'
                              }
                              implementation 'com.squareup.retrofit2:converter-gson:2.1.0'
                              implementation 'com.squareup.okhttp3:logging-interceptor:3.4.1'
                              implementation 'com.squareup.okhttp3:okhttp:3.4.1'
Butterknife                 : implementation 'com.jakewharton:butterknife:8.8.1'
                              annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'