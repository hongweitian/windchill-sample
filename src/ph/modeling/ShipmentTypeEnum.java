package ph.modeling;
import com.ptc.windchill.annotations.FrozenSerializable;
import com.ptc.windchill.annotations.metadata.GenAsEnumeratedType;
import com.ptc.windchill.annotations.metadata.SupportedAPI;

import wt.fc.DynamicEnumType;

@GenAsEnumeratedType(superClass=DynamicEnumType.class, supportedAPI=SupportedAPI.PUBLIC)
@FrozenSerializable
public class ShipmentTypeEnum extends _ShipmentTypeEnum {
	private static final long serialVersionUID = 1;
}
