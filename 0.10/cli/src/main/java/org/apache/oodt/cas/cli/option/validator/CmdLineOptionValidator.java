/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.oodt.cas.cli.option.validator;

//OODT imports
import org.apache.oodt.cas.cli.option.CmdLineOptionInstance;

/**
 * Validator for specified values of {@link CmdLineOption}s.
 * 
 * @author bfoster (Brian Foster)
 */
public interface CmdLineOptionValidator {

   public class Result {
      public enum Grade {
         PASS, FAIL;
      }

      private String message;
      private Grade grade;

      public Result(Grade grade, String message) {
         this.message = message;
         this.grade = grade;
      }

      public String getMessage() {
         return message;
      }

      public Grade getGrade() {
         return grade;
      }
   }

   /**
    * Throws {@link CmdLineValidationException} if validation fails, otherwise
    * method just returns.
    */
   public Result validate(CmdLineOptionInstance optionInst);
}
