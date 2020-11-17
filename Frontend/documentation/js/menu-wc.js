'use strict';


customElements.define('compodoc-menu', class extends HTMLElement {
    constructor() {
        super();
        this.isNormalMode = this.getAttribute('mode') === 'normal';
    }

    connectedCallback() {
        this.render(this.isNormalMode);
    }

    render(isNormalMode) {
        let tp = lithtml.html(`
        <nav>
            <ul class="list">
                <li class="title">
                    <a href="index.html" data-type="index-link">proposal-system documentation</a>
                </li>

                <li class="divider"></li>
                ${ isNormalMode ? `<div id="book-search-input" role="search"><input type="text" placeholder="Type to search"></div>` : '' }
                <li class="chapter">
                    <a data-type="chapter-link" href="index.html"><span class="icon ion-ios-home"></span>Getting started</a>
                    <ul class="links">
                        <li class="link">
                            <a href="overview.html" data-type="chapter-link">
                                <span class="icon ion-ios-keypad"></span>Overview
                            </a>
                        </li>
                        <li class="link">
                            <a href="index.html" data-type="chapter-link">
                                <span class="icon ion-ios-paper"></span>README
                            </a>
                        </li>
                                <li class="link">
                                    <a href="dependencies.html" data-type="chapter-link">
                                        <span class="icon ion-ios-list"></span>Dependencies
                                    </a>
                                </li>
                    </ul>
                </li>
                    <li class="chapter modules">
                        <a data-type="chapter-link" href="modules.html">
                            <div class="menu-toggler linked" data-toggle="collapse" ${ isNormalMode ?
                                'data-target="#modules-links"' : 'data-target="#xs-modules-links"' }>
                                <span class="icon ion-ios-archive"></span>
                                <span class="link-name">Modules</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                        </a>
                        <ul class="links collapse " ${ isNormalMode ? 'id="modules-links"' : 'id="xs-modules-links"' }>
                            <li class="link">
                                <a href="modules/AppModule.html" data-type="entity-link">AppModule</a>
                                    <li class="chapter inner">
                                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                            'data-target="#components-links-module-AppModule-08019713eb20d6465f0da0e9cd1360a9"' : 'data-target="#xs-components-links-module-AppModule-08019713eb20d6465f0da0e9cd1360a9"' }>
                                            <span class="icon ion-md-cog"></span>
                                            <span>Components</span>
                                            <span class="icon ion-ios-arrow-down"></span>
                                        </div>
                                        <ul class="links collapse" ${ isNormalMode ? 'id="components-links-module-AppModule-08019713eb20d6465f0da0e9cd1360a9"' :
                                            'id="xs-components-links-module-AppModule-08019713eb20d6465f0da0e9cd1360a9"' }>
                                            <li class="link">
                                                <a href="components/ApiResponseComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ApiResponseComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/AppComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">AppComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CommentComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">CommentComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/CreateProposalComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">CreateProposalComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ErrorComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ErrorComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FeedComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">FeedComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/FilterComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">FilterComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ForgetPasswordComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ForgetPasswordComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/HomeComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">HomeComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/LandingPageComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">LandingPageComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/LoginComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">LoginComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/PasswordSpecsComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">PasswordSpecsComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/RegisterComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">RegisterComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ResetLinkComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ResetLinkComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/ShareProposalComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">ShareProposalComponent</a>
                                            </li>
                                            <li class="link">
                                                <a href="components/TeamComponent.html"
                                                    data-type="entity-link" data-context="sub-entity" data-context-id="modules">TeamComponent</a>
                                            </li>
                                        </ul>
                                    </li>
                                <li class="chapter inner">
                                    <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ?
                                        'data-target="#injectables-links-module-AppModule-08019713eb20d6465f0da0e9cd1360a9"' : 'data-target="#xs-injectables-links-module-AppModule-08019713eb20d6465f0da0e9cd1360a9"' }>
                                        <span class="icon ion-md-arrow-round-down"></span>
                                        <span>Injectables</span>
                                        <span class="icon ion-ios-arrow-down"></span>
                                    </div>
                                    <ul class="links collapse" ${ isNormalMode ? 'id="injectables-links-module-AppModule-08019713eb20d6465f0da0e9cd1360a9"' :
                                        'id="xs-injectables-links-module-AppModule-08019713eb20d6465f0da0e9cd1360a9"' }>
                                        <li class="link">
                                            <a href="injectables/GetProposalsService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>GetProposalsService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/GetTeamService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>GetTeamService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/PostProposalService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>PostProposalService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/ProposalService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>ProposalService</a>
                                        </li>
                                        <li class="link">
                                            <a href="injectables/UserRegisterService.html"
                                                data-type="entity-link" data-context="sub-entity" data-context-id="modules" }>UserRegisterService</a>
                                        </li>
                                    </ul>
                                </li>
                            </li>
                            <li class="link">
                                <a href="modules/AppRoutingModule.html" data-type="entity-link">AppRoutingModule</a>
                            </li>
                </ul>
                </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#classes-links"' :
                            'data-target="#xs-classes-links"' }>
                            <span class="icon ion-ios-paper"></span>
                            <span>Classes</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="classes-links"' : 'id="xs-classes-links"' }>
                            <li class="link">
                                <a href="classes/Comment.html" data-type="entity-link">Comment</a>
                            </li>
                            <li class="link">
                                <a href="classes/FeedParams.html" data-type="entity-link">FeedParams</a>
                            </li>
                            <li class="link">
                                <a href="classes/MyErrorStateMatcher.html" data-type="entity-link">MyErrorStateMatcher</a>
                            </li>
                            <li class="link">
                                <a href="classes/Post.html" data-type="entity-link">Post</a>
                            </li>
                            <li class="link">
                                <a href="classes/Proposal.html" data-type="entity-link">Proposal</a>
                            </li>
                            <li class="link">
                                <a href="classes/teamList.html" data-type="entity-link">teamList</a>
                            </li>
                            <li class="link">
                                <a href="classes/Teams.html" data-type="entity-link">Teams</a>
                            </li>
                            <li class="link">
                                <a href="classes/User.html" data-type="entity-link">User</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#injectables-links"' :
                                'data-target="#xs-injectables-links"' }>
                                <span class="icon ion-md-arrow-round-down"></span>
                                <span>Injectables</span>
                                <span class="icon ion-ios-arrow-down"></span>
                            </div>
                            <ul class="links collapse " ${ isNormalMode ? 'id="injectables-links"' : 'id="xs-injectables-links"' }>
                                <li class="link">
                                    <a href="injectables/AuthorizationService.html" data-type="entity-link">AuthorizationService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/GetProposalsService.html" data-type="entity-link">GetProposalsService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/GetTeamService.html" data-type="entity-link">GetTeamService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/PostProposalService.html" data-type="entity-link">PostProposalService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ProposalService.html" data-type="entity-link">ProposalService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ResetPasswordService.html" data-type="entity-link">ResetPasswordService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/SendResetLinkService.html" data-type="entity-link">SendResetLinkService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/SocialMediaAuthService.html" data-type="entity-link">SocialMediaAuthService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/TeamsService.html" data-type="entity-link">TeamsService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/TestServiceService.html" data-type="entity-link">TestServiceService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/TransferDataService.html" data-type="entity-link">TransferDataService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UserLoginService.html" data-type="entity-link">UserLoginService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UserRegisterService.html" data-type="entity-link">UserRegisterService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/UserService.html" data-type="entity-link">UserService</a>
                                </li>
                                <li class="link">
                                    <a href="injectables/ValidateTokenService.html" data-type="entity-link">ValidateTokenService</a>
                                </li>
                            </ul>
                        </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#interceptors-links"' :
                            'data-target="#xs-interceptors-links"' }>
                            <span class="icon ion-ios-swap"></span>
                            <span>Interceptors</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="interceptors-links"' : 'id="xs-interceptors-links"' }>
                            <li class="link">
                                <a href="interceptors/HttpInterceptorService.html" data-type="entity-link">HttpInterceptorService</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#interfaces-links"' :
                            'data-target="#xs-interfaces-links"' }>
                            <span class="icon ion-md-information-circle-outline"></span>
                            <span>Interfaces</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? ' id="interfaces-links"' : 'id="xs-interfaces-links"' }>
                            <li class="link">
                                <a href="interfaces/DialogData.html" data-type="entity-link">DialogData</a>
                            </li>
                        </ul>
                    </li>
                    <li class="chapter">
                        <div class="simple menu-toggler" data-toggle="collapse" ${ isNormalMode ? 'data-target="#miscellaneous-links"'
                            : 'data-target="#xs-miscellaneous-links"' }>
                            <span class="icon ion-ios-cube"></span>
                            <span>Miscellaneous</span>
                            <span class="icon ion-ios-arrow-down"></span>
                        </div>
                        <ul class="links collapse " ${ isNormalMode ? 'id="miscellaneous-links"' : 'id="xs-miscellaneous-links"' }>
                            <li class="link">
                                <a href="miscellaneous/functions.html" data-type="entity-link">Functions</a>
                            </li>
                            <li class="link">
                                <a href="miscellaneous/variables.html" data-type="entity-link">Variables</a>
                            </li>
                        </ul>
                    </li>
                        <li class="chapter">
                            <a data-type="chapter-link" href="routes.html"><span class="icon ion-ios-git-branch"></span>Routes</a>
                        </li>
                    <li class="chapter">
                        <a data-type="chapter-link" href="coverage.html"><span class="icon ion-ios-stats"></span>Documentation coverage</a>
                    </li>
                    <li class="divider"></li>
                    <li class="copyright">
                        Documentation generated using <a href="https://compodoc.app/" target="_blank">
                            <img data-src="images/compodoc-vectorise.png" class="img-responsive" data-type="compodoc-logo">
                        </a>
                    </li>
            </ul>
        </nav>
        `);
        this.innerHTML = tp.strings;
    }
});