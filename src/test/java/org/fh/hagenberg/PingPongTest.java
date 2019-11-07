package org.fh.hagenberg;

import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PingPongTest {

  private static ActorSystem system;

  @BeforeClass
  public static void setup() {
    system = ActorSystem.create();
  }

  @AfterClass
  public static void tearDown() {
    TestKit.shutdownActorSystem(system);
    system = null;
  }

  @Test
  public void testPing() {
    new TestKit(system) {{
      final var pingActor = system.actorOf(Ping.props());
      pingActor.tell("pong", getRef());

      expectMsg("ping");
    }};
  }

  @Test
  public void testPong() {
    new TestKit(system) {{
      final var pongActor = system.actorOf(Pong.props());
      pongActor.tell("ping", getRef());

      expectMsg("pong");
    }};
  }
}
