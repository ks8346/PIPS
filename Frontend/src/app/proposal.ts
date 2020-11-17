/** This is a proposal class resposible for handling proposal data */
export class Proposal {
    /**
     * this is the constructor of proposal class
     * @param userId This is the UserId from session.
     * @param title This is the title of the Proposal.
     * @param description This is the description of the Proposal.
     * @param teams This is the array of teams to which it shared.
     * @param {number} id This is the Proposal id. 
     */
    constructor(
        public userId:string,
        public title:string,
        public description:string,
        public teams:{}[],
        public id?:number
    ){}
}
