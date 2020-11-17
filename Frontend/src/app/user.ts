/**
 * defines the user data type
 */
export class User {
    constructor(
        /**define id of the user (unique) */
        public id:number,
        /**define name of the user (string) */
        public name:string,
        /**define email of the user (string) */
        public email:string,
        /**define team the user is in */
        public team:{
            /**define id of the team (unique) */
            id:number,
            /**define name of the team (string) */
            name:string
        }
    ){}
}