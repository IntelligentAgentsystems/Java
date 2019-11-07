package org.fh.hagenberg;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Ping extends AbstractActor {
  private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  static Props props() {
    return Props.create(Ping.class);
  }

  private int counter = 0;
  private ActorRef pongActor = getContext().actorOf(Pong.props(), "pongActor");

  private void handleMsg(String msg) {
    if (msg.equals("init")) {
      log.info("In PingActor - starting ping-pong");
      pongActor.tell("ping", getSelf());
    } else {
      log.info("In PingActor - received message: {}", msg);
      counter += 1;

      if (counter == 3) {
        getContext().system().terminate();
      } else {
        getSender().tell("ping", getSelf());
      }
    }
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(String.class, this::handleMsg)
        .build();
  }
}
