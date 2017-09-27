'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import SimpleLineChart from './lineChart.js';

class Gym extends React.Component {
  render() {
    return (
      <div className="align">
        <div className="align recentLifts">
          <h2>Gym Usage</h2>
         </div>
         <div id="align lineChart">
           <SimpleLineChart/>
          </div>
      </div>
    )
  }
}

export default Gym;
