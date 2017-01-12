package com.lenovo.plm.ebg.arbortext;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ShipmentTypeEnum extends wt.fc.DynamicEnumType {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "com.lenovo.plm.ebg.arbortext.arbortextResource";
   static final java.lang.String CLASSNAME = new ShipmentTypeEnum().getClass().getName();

   static final java.lang.String CLASS_RESOURCE = "com.lenovo.plm.ebg.arbortext.ShipmentTypeEnumRB";
   @SuppressWarnings("rawtypes") static java.util.Hashtable localeSets;
   static final java.util.Map<String, ShipmentTypeEnum> dynamicSet = new java.util.concurrent.ConcurrentHashMap<>();

   private static volatile wt.fc.EnumeratedType[] valueSet;
   static wt.fc.EnumeratedType[] _valueSet() {
      if (valueSet == null) synchronized (_ShipmentTypeEnum.class) {
         try { if (valueSet == null) valueSet = initializeLocaleSet(null); }
         catch (java.lang.Throwable t) { throw new java.lang.ExceptionInInitializerError(t); }
      }
      return valueSet;
   }

   public static ShipmentTypeEnum newShipmentTypeEnum(int secretHandshake) throws java.lang.IllegalAccessException {
      validateFriendship(secretHandshake);
      return new ShipmentTypeEnum();
   }

   /** <b>Supported API: </b>true **/
   public static ShipmentTypeEnum toShipmentTypeEnum(java.lang.String internal_value) throws wt.util.WTInvalidParameterException {
      ShipmentTypeEnum instance = null;
      try { instance = (ShipmentTypeEnum) toEnumeratedType(internal_value, _valueSet()); }
      catch (wt.util.WTInvalidParameterException wtipe) {
         instance = dynamicSet.get(internal_value);
         if (instance == null) {
            try { instance = newShipmentTypeEnum(secretHandshake); } catch (java.lang.IllegalAccessException iae) { }
            dynamicInstance(instance, internal_value, dynamicSet);
         }
      }
      return instance;
   }

   /** <b>Supported API: </b>true **/
   public static ShipmentTypeEnum getShipmentTypeEnumDefault() {
      return (ShipmentTypeEnum) defaultEnumeratedType(_valueSet());
   }

   /** <b>Supported API: </b>true **/
   public static ShipmentTypeEnum[] getShipmentTypeEnumSet() {
      ShipmentTypeEnum[] set = new ShipmentTypeEnum[_valueSet().length];
      java.lang.System.arraycopy(valueSet, 0, set, 0, valueSet.length);
      return set;
   }

   /** <b>Supported API: </b>true **/
   public wt.fc.EnumeratedType[] getValueSet() {
      return getShipmentTypeEnumSet();
   }

   protected wt.fc.EnumeratedType[] valueSet() {
      return _valueSet();
   }

   @SuppressWarnings("rawtypes")
   protected wt.fc.EnumeratedType[] getLocaleSet(java.util.Locale locale) {
      wt.fc.EnumeratedType[] request = null;

      if (localeSets == null) localeSets = new java.util.Hashtable();
      else request = (wt.fc.EnumeratedType[]) localeSets.get(locale);

      if (request == null) {
         try { request = initializeLocaleSet(locale); }
         catch (java.lang.Throwable t) { /* snuff, since generation of class ensures that exception will not be thrown */ }
         localeSets.put(locale, request);
      }

      return request;
   }

   static wt.fc.EnumeratedType[] initializeLocaleSet(java.util.Locale locale) throws java.lang.Throwable {
      return instantiateSet(ShipmentTypeEnum.class.getMethod( "newShipmentTypeEnum", new java.lang.Class<?>[] { java.lang.Integer.TYPE }), CLASS_RESOURCE, locale);
   }
}
