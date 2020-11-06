export class Proposal {
    constructor(
        public userId:string,
        public title:string,
        public description:string,
        public teams:{}[],
        public id?:number
    ){}
}
