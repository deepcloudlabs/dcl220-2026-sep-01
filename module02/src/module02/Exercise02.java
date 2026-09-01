package module02;

import java.math.BigDecimal;
import java.util.Map;

enum ShippingMethod {
	STANDARD, EXPRESS, OVERNIGHT, DRONE
}

interface ShippingPolicy {
	BigDecimal fee(BigDecimal orderTotal);
}

class StandardShippingPolicy implements ShippingPolicy {

	@Override
	public BigDecimal fee(BigDecimal orderTotal) {
		return new BigDecimal("5.00");
	}
	
}

class DroneShippingPolicy implements ShippingPolicy {
	
	@Override
	public BigDecimal fee(BigDecimal orderTotal) {
		return orderTotal.multiply(new BigDecimal("0.20")).add(new BigDecimal("30.00"));
	}
	
}
class ExpressShippingPolicy implements ShippingPolicy {
	
	@Override
	public BigDecimal fee(BigDecimal orderTotal) {
		return orderTotal.multiply(new BigDecimal("0.10"));
	}
	
}

class OvernightShippingPolicy implements ShippingPolicy {
	
	@Override
	public BigDecimal fee(BigDecimal orderTotal) {
		return new BigDecimal("30.00");
	}
	
}

class ShippingCalculator {
	private final Map<ShippingMethod,ShippingPolicy> shippingPolicies;
	
	public ShippingCalculator(Map<ShippingMethod, ShippingPolicy> shippingPolicies) {
		this.shippingPolicies = shippingPolicies;
	}

	BigDecimal calculate(ShippingMethod method, BigDecimal orderTotal) {
		var shippingPolicy = shippingPolicies.get(method);
		return shippingPolicy.fee(orderTotal);
	}
}

public class Exercise02 {
	private final static Map<ShippingMethod,ShippingPolicy> ShippingPolicies = Map.of(
			ShippingMethod.STANDARD, new StandardShippingPolicy(),
			ShippingMethod.EXPRESS, new ExpressShippingPolicy(),
			ShippingMethod.OVERNIGHT, new OvernightShippingPolicy(),
			ShippingMethod.DRONE, new DroneShippingPolicy()
	);

	public static void main(String[] args) {
		ShippingCalculator goodCalculator = new ShippingCalculator(ShippingPolicies);
		System.out.println("STANDARD fee: %f".formatted(goodCalculator.calculate(ShippingMethod.STANDARD, BigDecimal.valueOf(240L))));
		System.out.println("EXPRESS fee: %f".formatted(goodCalculator.calculate(ShippingMethod.EXPRESS, BigDecimal.valueOf(240L))));
		System.out.println("OVERNIGHT fee: %f".formatted(goodCalculator.calculate(ShippingMethod.OVERNIGHT, BigDecimal.valueOf(240L))));
		System.out.println("DRONE fee: %f".formatted(goodCalculator.calculate(ShippingMethod.DRONE, BigDecimal.valueOf(240L))));

	}

}
