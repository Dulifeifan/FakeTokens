# FakeTokens
Course Project - Natural Language Processing

For compiling the program:

There is nothing special to modify when compiling this program. Use °∞cd°± command to enter the directory of the file. Use °∞chmod u+x filename°± to add permissions to.sh files. Then, use°±./°± or °∞sh°± command to run both .sh files. For °∞build_java.sh°±, there should be five parameters: "tokenize", "estimate", "predict", °∞generate°±, °∞evaluate°±. For °∞run_java.sh°±, there should be four parameters corresponding to each java file, °∞tokenize°±, °∞estimate°±, °∞predict°±, °∞evaluate°±. 


Something special in this program:

°§The use of map mapping coordinates when storing two-dimensional matrices greatly reduces the time complexity of the program.
°§A similar idea is used when counting Nc, and Nc is stored in a one-dimensional array. The coordinates of the one-dimensional array are the corresponding C.
°§Export files in a rigorous format to pass data in two java files.
°§When it is finally determined whether the word needs to be changed, it is first determined whether the corresponding confusing word and the pre-existing word appear in the training set. This will reduce the number of judgments and make the program logic clearer.
