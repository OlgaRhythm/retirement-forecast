import cl from './HomePage.module.css'
import { useState } from 'react';
import Header from '../../components/Header/Header';
import Preview from 'rc-image/lib/Preview'; 
import FileUpload from '../../components/FileManager/FileUpload';
import Title from 'antd/es/skeleton/Title';
import ResultView from '../../components/ResultView/ResultView';
import PreviewField from '../../components/PreviewField/PreviewField';

const HomePage = () => {

    return (
        <div className={cl.container}>

            <Header />

            <ResultView />

            {/*<PreviewField />*/}

        </div>
    )
}

export default HomePage