plugins {
    id("java")
}

group = "rspsi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://spring-rich-c.sourceforge.net/maven2repository/")
    }
}

dependencies {
    // https://mvnrepository.com/artifact/com.l2fprod/l2fprod-common-all
    implementation("com.l2fprod:l2fprod-common-all:7.3")
}