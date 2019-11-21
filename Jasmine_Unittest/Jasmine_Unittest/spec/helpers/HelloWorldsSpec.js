define(["lib/jasmine/src/notepad"], function (notepad) {
    describe("returns titles", function () {

        it("something", function() {

            expect(notepad.noteTitles()).toEqual("pick up the kids get milk");
        });

    });
   describe("Hello World", function() { 
   
    it("should Return Hello world",function() { 
       expect(helloworld()).toEqual('Hello World'); 
    }); 
 
   });
})