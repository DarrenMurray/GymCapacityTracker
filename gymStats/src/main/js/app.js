'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, browserHistory, IndexRoute} from 'react-router-dom';
import Gym from './components/gym';

ReactDOM.render((
  <Router history={browserHistory}>
    <div>
      <Route component={Gym} path="/index.html"/>
    </div>
  </Router>),
  document.getElementById('react'));