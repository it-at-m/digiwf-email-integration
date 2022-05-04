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
    It can be used to send mails asynchronously through a spring cloud stream compatible event broker.<br>For attachments to not clutter the EventBus,
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

The goal of this library is enabling async mail dispatching with a EventBus and a S3-Filer as your environment.

Features:

* Can be used to dispatch emails asynchronously through an eventbus.
* Can inform the receiver through an eventbus if the email has been sent or if there was a problem.
* Can get attachment files from an S3-Filer, no need to get them yourself and send it through the EventBus several
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

For an explanation on how to get started, please refer to
the [quickstart doc](https://github.com/it-at-m/digiwf-email-integration/blob/add-docs/docs/quickstart.md).

<p align="right">(<a href="#top">back to top</a>)</p>

## Documentation

For all further documentation, please refer to
the [documentations doc](https://github.com/it-at-m/digiwf-email-integration/blob/add-docs/docs/documentation.md).

<p align="right">(<a href="#top">back to top</a>)</p>

## Architecture

For a small impression of the architecture, please refer to
the [architecture doc](https://github.com/it-at-m/digiwf-email-integration/blob/add-docs/docs/architecture.md).

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