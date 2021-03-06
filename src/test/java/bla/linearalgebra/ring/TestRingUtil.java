package bla.linearalgebra.ring;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import bla.linearalgebra.IRing;
import bla.linearalgebra.impl.RingUtil;
import bla.linearalgebra.integer.TestBigIntegerRing;

public class TestRingUtil {
	protected TestBigIntegerRing parentTestClass = new TestBigIntegerRing();

	@Test
	public void testPower() {
		Assert.assertEquals(parentTestClass.makeRing().makeFromInt(64),
				RingUtil.power(parentTestClass.makeRing(), parentTestClass.makeRing().makeFromInt(2), 6));
	}

	@Test
	public void testXGCD() {
		IRing<BigInteger> ring = parentTestClass.makeRing();

		List<BigInteger> output = RingUtil.XGCD(ring, ring.makeFromInt(13), ring.makeFromInt(7));

		Assert.assertEquals(Arrays.asList(BigInteger.valueOf(-1), BigInteger.valueOf(2), BigInteger.valueOf(1)), output);

		Assert.assertEquals(ring.makeFromInt(4), RingUtil.XGCD(ring, ring.makeFromInt(4), ring.makeFromInt(16)).get(2));
		Assert.assertEquals(ring.makeFromInt(4), RingUtil.XGCD(ring, ring.makeFromInt(16), ring.makeFromInt(4)).get(2));
		Assert.assertEquals(ring.makeFromInt(15), RingUtil.XGCD(ring, ring.makeFromInt(15), ring.makeFromInt(60)).get(2));
		Assert.assertEquals(ring.makeFromInt(5), RingUtil.XGCD(ring, ring.makeFromInt(15), ring.makeFromInt(65)).get(2));
		Assert.assertEquals(ring.makeFromInt(4), RingUtil.XGCD(ring, ring.makeFromInt(1052), ring.makeFromInt(52)).get(2));
	}
}
