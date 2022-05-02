<div id="top"></div>

<!-- PROJECT SHIELDS -->

<!-- END OF PROJECT SHIELDS -->

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="#">
    <img src="/images/logo.png" alt="Logo" height="200">
  </a>

<h3 align="center">DigiWF E-Mail Integration</h3>

  <p align="center">
    This is a Spring Boot Starter library to send e-mails in the DigiWF environment.
    It can be used to send mails asynchronously through kafka.<br>For attachments to not clutter the EventBus,
    paths to file locations on the S3-Filer are expected. The file is then grabbed by this library to avoid sending
    large files repeatedly through the EventBus.
<br /><a href="#">Report Bug</a>
    ·
    <a href="#">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#roadmap">Roadmap</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li>
      <a href="#Documentation">Documentation</a>
    </li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

The goal of this library is enabling async mail dispatching with a Kafka EventBus and a S3-Filer as your environment.

Features:

* Can be used to dispatch emails asynchronously through kafka.
* Can inform the receiver through kafka if the email has been sent or if there was a problem.
* Can get attachment files from an S3-Filer, no need to get them yourself and send it through the Kafka EventBus several
  times. Less clutter in your EventBus!

<p align="right">(<a href="#top">back to top</a>)</p>

### Built With

This project is built with:

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream)
* [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-spring-cloudstream-utils)
* [DigiWF S3 Integration](https://github.com/it-at-m/digiwf-s3-integration)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ROADMAP -->

## Roadmap

See the [open issues](#) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>

## Getting started

Below is an example of how you can install and set up your service.

1. Use the spring initalizer and create a Spring Boot application with `Spring Web`
   dependencies [https://start.spring.io](https://start.spring.io)
2. Add the digiwf-email-integration-starter dependency.

With Maven:

```
   <dependency>
        <groupId>io.muenchendigital.digiwf</groupId>
        <artifactId>digiwf-email-integration-starter</artifactId>
        <version>${digiwf.version}</version>
   </dependency>
```

With Gradle:

```
implementation group: 'io.muenchendigital.digiwf', name: 'digiwf-email-integration-starter', version: '${digiwf.version}'
```

3. Add your preferred binder (see [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream)). In this
   example, we use kafka.

Maven:

 ```
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-stream-binder-kafka</artifactId>
     </dependency>
```

Gradle:

```
implementation group: 'org.springframework.cloud', name: 'spring-cloud-stream-binder-kafka'
```

4. Configure your binder.<br>
   For an example on how to configure your binder,
   see [DigiWF Spring Cloudstream Utils](https://github.com/it-at-m/digiwf-spring-cloudstream-utils#getting-started)
   Note that you DO have to
   configure ```spring.cloud.function.definition=functionRouter;sendMessage;sendCorrelateMessage;```, but you don't need
   typeMappings. These are configured for you by the digiwf-mail-integration-starter. You also have to configure the
   topics you want to read / send messages from / to.

5. Configure S3

```
io.muenchendigital.digiwf.s3.client.document-storage-url: http://s3-integration-url:port
```

See [this](https://github.com/it-at-m/digiwf-spring-cloudstream-utils) for an example.

6. Configure your application

```
spring:
  mail:
    host: mail.example.com
    port: 587
    username: mymail@example.de
    password: yourExcellentPassword
    properties:
      mail.debug: false
      mail.tls: true
      mail.transport.protocol: smtp
      mail.smtp.host: mail.example.com
      mail.smtp.port: 587
      mail.smtp.connectiontimeout: 10000
      mail.smtp.timeout: 10000
      mail.smtp.auth: true
      mail.smtp.ssl.trust: "*"
      mail.smtp.ssl.checkserveridentity: true
      mail.smtp.socketFactory.fallback: true
      mail.smtp.starttls.enable: true
```

You can also use digiwf.mail.fromAdress to define a mail adress when not using smtp.auth.

7. Define a RestTemplate. For an example, please refer to
   the [example project](https://github.com/it-at-m/digiwf-email-integration/tree/dev/example).

<p align="right">(<a href="#top">back to top</a>)</p>

## Documentation

To send an e-mail through the eventbus, simply create
a [Mail](https://github.com/it-at-m/digiwf-email-integration/tree/dev/digiwf-email-integration/src/main/java/io/muenchendigital/digiwf/email/integration/domain/model/Mail.java)
object, set the TYPE-Header
to [MessageProcessor.TYPE_HEADER_SEND_MAIL_FROM_EVENT_BUS](https://github.com/it-at-m/digiwf-email-integration/tree/dev/digiwf-email-integration/src/main/java/io/muenchendigital/digiwf/email/integration/domain/streaming/MessageProcessor.java)
and send it to the corresponding kafka topic. That's it!

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please open an issue with the tag "enhancement", fork the repo and
create a pull request. You can also simply open an issue with the tag "enhancement". Don't forget to give the project a
star! Thanks again!

1. Open an issue with the tag "enhancement"
2. Fork the Project
3. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
4. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
5. Push to the Branch (`git push origin feature/AmazingFeature`)
6. Open a Pull Request

More about this in the [CODE_OF_CONDUCT](/CODE_OF_CONDUCT.md) file.

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE` file for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->

## Contact

it@m - opensource@muenchendigital.io

[join our slack channel](https://join.slack.com/t/digiwf/shared_invite/zt-14jxazj1j-jq0WNtXp7S7HAwJA7tKgpw)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->