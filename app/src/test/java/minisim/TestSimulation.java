package minisim;

import minisim.simulation.Body;
import minisim.simulation.Simulation;
import minisim.simulation.V2;
import minisim.simulation.force.Friction;
import minisim.simulation.force.Gravity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSimulation {

	private Simulation sim;

	@BeforeEach
	public void setup() {
		sim = Simulation.builder().width(100).height(100).addForce(new Gravity(1e-8)).addForce(new Friction(0.99))
				.solidBorders().build();
	}

	@Test
	public void canDetectCollisions() {
		sim.addBody(new Body(new V2(1, 1), new V2(0, 0), 1, 1));
		sim.addBody(new Body(new V2(2, 2), new V2(0, 0), 1, 1));
		assertTrue(sim.detectAndResolveCollisions());
	}

	@Test
	public void noCollisions() {
		sim.addBody(new Body(new V2(1, 1), new V2(0, 0), 1, 1));
		sim.addBody(new Body(new V2(3, 3), new V2(0, 0), 1, 1));
		assertFalse(sim.detectAndResolveCollisions());
	}

	@Test
	public void canResolveCollisions() {
		sim.addBody(new Body(new V2(1, 2), new V2(0, 0), 1, 1));
		sim.addBody(new Body(new V2(2, 2), new V2(0, 0), 1, 1));
		assertTrue(sim.detectAndResolveCollisions());
		assertFalse(sim.detectAndResolveCollisions());
		assertEquals(sim.getBodies().get(0).position, new V2(0.5, 2));
		assertEquals(sim.getBodies().get(1).position, new V2(2.5, 2));
	}

	@Test
	public void canResolveCollisionsDifferentMasses() {
		// this test is a copy-paste of "can_resolve_collisions" because mass
		// must not interfere with collisions
		sim.addBody(new Body(new V2(1, 2), new V2(0, 0), 2, 1));
		sim.addBody(new Body(new V2(2, 2), new V2(0, 0), 3, 1));
		assertTrue(sim.detectAndResolveCollisions());
		assertFalse(sim.detectAndResolveCollisions());
		assertEquals(sim.getBodies().get(0).position, new V2(0.5, 2));
		assertEquals(sim.getBodies().get(1).position, new V2(2.5, 2));
	}

	@Test
	public void canResolveCollisionsDifferentRadii() {
		// this test is an adapted copy-paste of "can_resolve_collisions" because
		// radii must not interfere with collisions
		sim.addBody(new Body(new V2(4, 4), new V2(0, 0), 1, 2));
		sim.addBody(new Body(new V2(5, 4), new V2(0, 0), 1, 3));
		assertTrue(sim.detectAndResolveCollisions());
		assertFalse(sim.detectAndResolveCollisions());
		assertEquals(sim.getBodies().get(0).position, new V2(2, 4));
		assertEquals(sim.getBodies().get(1).position, new V2(7, 4));
	}
}
