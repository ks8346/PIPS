export class Post {
    constructor(
        public creationDate: Date,  
        public description: string,
        public id: number,
        public teams:[],
        public title: string,
        public upvotesCount: number,
        public user: {
            id: number,
            name: string,
            email: string,
            teams:{
                id:number,
                name:string
            }
        } 
    )
    {}
}
