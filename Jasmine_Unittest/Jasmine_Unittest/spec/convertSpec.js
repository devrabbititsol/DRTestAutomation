var conversion =require('../src/convert.js');
var con = new conversion();
 describe( "Convert library", function () { 
   it("throws an error when passed an unknown from-unit", function () {
      var testFn = function () {
    	  con.Convert(1, "dollar").to("yens");
       }
      expect(testFn).toThrow(new Error("unrecognized from-unit"));
   });

   it("throws an error when passed an unknown to-unit", function () {
      var testFn = function () {
    	  con.Convert(1, "cm").to("furlongs");
       }
      expect(testFn).toThrow(new Error("unrecognized to-unit"));
   });
 });

 describe( "distance converter", function () {
    it("converts inches to centimeters", function () {
        expect(con.Convert(12, "inches").to("cm")).toEqual(30.48);
    });
    it("validating inches to centimeters", function () {
        expect(con.Convert(1, "inches").to("cm")).not.toBe(1);
    });
    it("converts feet to meter", function () {
        expect(con.Convert(1, "feet").to("meters")).toEqual(0.30);
    });
    
    it("converts centimeters to yards", function () {
    	console.log("A simple spec in level 2");
        expect(con.Convert(2000, "cm").to("yards")).toEqual(21.87);
    });
 });

 describe( "volume converter", function () {
    it("converts liters to gallons", function () {
    	console.log("A simple spec in level 3");
        expect(con.Convert(3, "liters").to("gallons")).toEqual(0.79);
    });
    it("validating gallons to litres", function () {
        expect(con.Convert(1, "gallons").to("liters")).not.toBeNull();
    });
    it("converts gallons to cups", function () {
        expect(con.Convert(2, "gallons").to("cups")).toEqual(32);
    });
    it("validating cups to litres", function () {
        expect(con.Convert(1, "cups").to("liters")).not.toBeCloseTo(1);
    });
 });
 describe( "temperature converter", function () {
    it("converts celsius to fahrenheit", function () {
        expect(con.Convert(3, "celsius").to("fahrenheit")).toEqual(37.4);
        
    });
    it("validating celsius to fahrenheit conversion", function () {
        expect(con.Convert(0, "celsius").to("fahrenheit")).not.toBeNull();
        
    });
    it("converts fahrenheit to celsius", function () {
        expect(con.Convert(72, "fahrenheit").to("celsius")).toEqual(22.22);
    });
    it("validating fahrenheit to celsius conversion", function () {
    	expect(con.Convert(0, "fahrenheit").to("celsius")).not.toBeGreaterThan(0);	       
    });    
 });