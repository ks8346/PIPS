/**
   * defines the comment object 
  */
export class Comment {
    constructor(
        /**define id of the comment (unique) */
        public id:number,
        /**define string type comment*/
        public comment:string,
        /**define the date of creation*/
        public creationDate:Date,
        /**define user data that posted the comment*/
        public user:{
            /**define id of the user*/
            id:number,
            /**define name of the user */
            name:string
        }
        
    ){}
}
