package org.fh.hagenberg;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Pong extends UntypedAbstractActor {
  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  static Props props() {
    return Props.create(Pong.class);
  }

  static class PongMessage {
    private final String text;

    PongMessage(String text) {
      this.text = text;
    }

    String getText() {
      return text;
    }
  }

  public void onReceive(Object message) {
    if (message instanceof Ping.PingMessage) {
      Ping.PingMessage ping = (Ping.PingMessage) message;
      log.info("In PongActor - received message: {}", ping.getText());
      getSender().tell(new PongMessage("pong"), getSelf());
    } else {
      unhandled(message);
    }
  }
}
