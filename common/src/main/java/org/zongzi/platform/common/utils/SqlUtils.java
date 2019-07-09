package org.zongzi.platform.common.utils;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.zongzi.platform.common.BaseMapper;

import java.util.Collection;
import java.util.List;

/**
 * 批量新增工具类
 *
 * @author haopeisong
 * Created by haopeisong on 2019-05-21
 */
public class SqlUtils {

    /**
     * 批量新增每次提交的数据量
     */
    private static final int BATCH_SIZE = 100;

    /**
     * <h4>批量新增</h4>
     *
     * @param cls       数据访问接口类的Class对象
     * @param entities  待新增的实体对象列表
     * @param batchSize 每次提交数量
     * @param <BM>      数据访问接口类
     * @param <TT>      数据访问接口的泛型参数
     */
    private static <BM extends BaseMapper<TT>, TT> void batchSave(Class<BM> cls, Collection<TT> entities, int batchSize) {
        int i = 0;
        SqlSessionFactory sqlSessionFactory = AppContextUtils.getBean(SqlSessionFactory.class);
        String statement = cls.getName() + ".insertSelective";
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            for (TT entity : entities) {
                sqlSession.insert(statement, entity);
                if (i >= 1 && i % batchSize == 0) {
                    sqlSession.flushStatements();
                }
                i++;
            }
            sqlSession.flushStatements();
        }
    }

    /**
     * <h4>批量更新</h4>
     *
     * @param cls       数据访问接口类的Class对象
     * @param entities  待新增的实体对象列表
     * @param batchSize 每次提交数量
     * @param <BM>      数据访问接口类
     * @param <TT>      数据访问接口的泛型参数
     */
    private static <BM extends BaseMapper<TT>, TT> void updateBatch(Class<BM> cls, List<TT> entities, int batchSize) {
        int i = 0;
        SqlSessionFactory sqlSessionFactory = AppContextUtils.getBean(SqlSessionFactory.class);
        String statement = cls.getName() + ".updateByPrimaryKeySelective";
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
            for (TT entity : entities) {
                sqlSession.update(statement, entity);
                if (i >= 1 && i % batchSize == 0) {
                    sqlSession.flushStatements();
                }
                i++;
            }
            sqlSession.flushStatements();
        }
    }

    /**
     * <h4>批量新增</h4>
     *
     * @param cls      数据访问接口类的Class
     * @param entities 待新增的实体对象列表
     * @param <BM>     数据访问接口类
     * @param <TT>     数据访问接口的泛型参数
     */
    public static <BM extends BaseMapper<TT>, TT> void batchSave(Class<BM> cls, Collection<TT> entities) {
        batchSave(cls, entities, BATCH_SIZE);
    }

    /**
     * <h4>批量更新</h4>
     *
     * @param cls      数据访问接口类的Class
     * @param entities 待新增的实体对象列表
     * @param <BM>     数据访问接口类
     * @param <TT>     数据访问接口的泛型参数
     */
    public static <BM extends BaseMapper<TT>, TT> void updateBatch(Class<BM> cls, List<TT> entities) {
        updateBatch(cls, entities, BATCH_SIZE);
    }
}
