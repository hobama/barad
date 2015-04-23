# Introduction #

When we first thought of Barad we wanted to design an automated testing tool for SWT applications.  The ability to automatically generate test cases is a major requirement.  Ease of use and a feature to view the widget hierarchy of an SWT application are also important requirements.

# Requirements #

## Major Requirements ##

1. _Automated test case generation_

Test cases should be automatically generated, the faster the better.  Guided feedback from the end user can be inputted that will constrain the GUI state space considered by the generation algorithm.  An option to achieve maximum GUI state coverage is required, as is an option to discover maximum defects.

2. _Ease of use_

The tool should be easy to install and use.  Access to source code for the AUT cannot be assumed, and therefore the design cannot use source instrumentation.  A minimal amount of changes to the environment of the AUT in order for the tool to work is required.

## Nice to Have Requirements ##

1. _View widget hierarchy_

A view of the widget hierarchy along with their associate runtime values is a convenient tool to use for developers and testers.  It's also something that will be useful to have in order to address the test case generation requirement.

### Terminology ###

SWT: [Standard Widget Toolkit](http://www.eclipse.org/swt/)

AUT: Application Under Test