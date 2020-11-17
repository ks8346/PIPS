/**
 * The object type of a post that is created
*/
export class Post {
    constructor(  
        /**define description of a post (string)*/
        public description: string,
        /**define id of the post (unique) */
        public id: number,
        /**define teams array that its shared to */
        public teams:[],
        /**define title of the post (string) */
        public title: string,
        /**define number of upvotes */
        public upvotesCount: number,
        /**define the user data */
        public user: {
            /**define id of the user (unique) */
            id: number,
            /**define name of the user */
            name: string,
            /**define email of the user */
            email: string,
            /**define team the user is in */
            teams:{
                /**define id of the team (unique) */
                id:number,
                /**define name of the team (string) */
                name:string
            }
        } 
    )
    {}
}
