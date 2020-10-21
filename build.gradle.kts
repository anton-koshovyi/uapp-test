plugins {
  id("org.gradle.jacoco")
  id("org.jetbrains.kotlin.jvm").version("1.4.10")
  id("org.jetbrains.kotlin.kapt").version("1.4.10")
  id("org.jetbrains.kotlin.plugin.jpa").version("1.4.10")
  id("org.jetbrains.kotlin.plugin.noarg").version("1.4.10")
  id("org.jetbrains.kotlin.plugin.spring").version("1.4.10")
  id("org.springframework.boot").version("2.2.0.RELEASE")
}

repositories {
  mavenCentral()
}

dependencies {

  // CDI
  implementation("org.springframework:spring-context:5.2.0.RELEASE")
  implementation("org.springframework.boot:spring-boot-autoconfigure:2.2.0.RELEASE")

  // Common
  implementation("com.google.guava:guava:29.0-jre")
  implementation("org.apache.commons:commons-lang3:3.9")
  implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.10")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.10")
  implementation("org.mapstruct:mapstruct:1.4.1.Final")
  kapt("org.mapstruct:mapstruct-processor:1.4.1.Final")
  implementation("org.yaml:snakeyaml:1.25")

  // Data
  implementation("com.h2database:h2:1.4.197")
  implementation("com.zaxxer:HikariCP:3.4.1")
  implementation("org.hibernate:hibernate-core:5.4.10.Final")
  implementation("org.springframework.data:spring-data-jpa:2.2.0.RELEASE")

  // Logging
  implementation("ch.qos.logback:logback-classic:1.2.3")
  implementation("org.slf4j:jul-to-slf4j:1.7.30")
  implementation("org.slf4j:slf4j-api:1.7.30")

  // Web
  implementation("com.fasterxml.jackson.core:jackson-databind:2.11.0")
  implementation("org.apache.tomcat.embed:tomcat-embed-core:9.0.37")
  implementation("org.springframework:spring-webmvc:5.2.0.RELEASE")


  // Test: Asserting
  testImplementation("org.assertj:assertj-core:3.16.1")
  testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
  testImplementation("org.skyscreamer:jsonassert:1.5.0")

  // Test: CDI
  testImplementation("org.springframework:spring-test:5.2.0.RELEASE")

  // Test: Web
  testImplementation("io.rest-assured:rest-assured:4.3.0")
  testImplementation("io.rest-assured:spring-mock-mvc:4.3.0")

}

tasks {
  compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }

  compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
  }

  test {
    useJUnitPlatform()
  }

  wrapper {
    gradleVersion = "5.6.4"
    distributionType = Wrapper.DistributionType.ALL
  }
}
