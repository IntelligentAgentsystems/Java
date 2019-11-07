package org.fh.hagenberg;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Ping extends UntypedAbstractActor {
  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  static Props props() {
    return Props.create(Ping.class);
  }

  static class Initialize {
  }

  static class PingMessage {
    private final String text;

    PingMessage(String text) {
      this.text = text;
    }

    String getText() {
      return text;
    }
  }

  private int counter = 0;
  private ActorRef pongActor = getContext().actorOf(Pong.props(), "pongActor");

  public void onReceive(Object message) {
    if (message instanceof Initialize) {
      log.info("In PingActor - starting ping-pong");
      pongActor.tell(new PingMessage("ping"), getSelf());
    } else if (message instanceof Pong.PongMessage) {
      Pong.PongMessage pong = (Pong.PongMessage) message;
      log.info("In PingActor - received message: {}", pong.getText());
      counter += 1;

      if (counter == 3) {
        getContext().system().terminate();
      } else {
        getSender().tell(new PingMessage("ping"), getSelf());
      }
    } else {
      unhandled(message);
    }
  }
}
