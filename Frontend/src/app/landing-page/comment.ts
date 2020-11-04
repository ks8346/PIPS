export class Comment {
    constructor(
        public id:number,
        public comment:string,
        public creationDate:Date,
        public user:{
            id:number,
            name:string
        }
        
    ){}
}
