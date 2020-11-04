export class FeedParams {
    constructor(
    public startDate:Date,
    public endDate:Date,
    public page:string,
    public size:string,
    public userId?,
    public teamId?
    ){}
}
