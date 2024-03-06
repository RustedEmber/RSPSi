plugins {
    id("java")
}

group = "rspsi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://spring-rich-c.sourceforge.net/maven2repository/")
    maven(url = "https://raw.githubusercontent.com/MatthewBishop/hosting/master")
}

dependencies {
    // https://mvnrepository.com/artifact/com.l2fprod/l2fprod-common-all
    implementation("com.l2fprod:l2fprod-common-all:7.3")

    // https://raw.githubusercontent.com/MatthewBishop/hosting/main/dev/advo/cachetool
    implementation("dev.advo:cachetool:1.0.0")
}