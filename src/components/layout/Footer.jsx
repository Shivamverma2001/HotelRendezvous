import React from 'react'
import { Col, Container, Row } from 'react-bootstrap';

const Footer = () => {
    let today=new Date();
    console.log("footer")
  return (
    <footer className='by-dark text-dark py-3 footer mt-lg-5'>
        <Container>
            <Row>
                <Col xs={12} md={12} className='text-center'>
                    <p className='mb-0'> &copy; {today.getFullYear()} Hotel Rendezvous</p>
                </Col>
            </Row>
        </Container>
    </footer>
  )
}

export default Footer
