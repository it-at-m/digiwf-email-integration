## Documentation

To send an e-mail through the eventbus, simply create
a [Mail](https://github.com/it-at-m/digiwf-email-integration/tree/dev/digiwf-email-integration/src/main/java/io/muenchendigital/digiwf/email/integration/domain/model/Mail.java)
object, set the TYPE-Header
to [MessageProcessor.TYPE_HEADER_SEND_MAIL_FROM_EVENT_BUS](https://github.com/it-at-m/digiwf-email-integration/tree/dev/digiwf-email-integration/src/main/java/io/muenchendigital/digiwf/email/integration/domain/streaming/MessageProcessor.java)
and send it to the corresponding kafka topic. That's it!